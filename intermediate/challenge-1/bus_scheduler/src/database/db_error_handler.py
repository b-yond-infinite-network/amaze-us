import collections
import re

from sqlalchemy import exc as sqla_exc

from src.exceptions.db_exception import DBException

_registry = collections.defaultdict(lambda: collections.defaultdict(list))


def filters(ame, exception_type, regex):
    """Mark a function as receiving a filtered exception."""

    def _receive(fn):
        _registry[ame][exception_type].extend(
            (fn, re.compile(reg))
            for reg in ((regex,) if not isinstance(regex, tuple) else regex)
        )
        return fn

    return _receive


# each @filters() lists a database name, a SQLAlchemy exception to catch,
# and a list of regular expressions that will be matched.  If all the
# conditions match, the handler is called which then raises a nicer
# error message.


@filters(
    "sqlite",
    sqla_exc.IntegrityError,
    (
        r"^.*columns?(?P<columns>[^)]+)(is|are)\s+not\s+unique$",
        r"^.*UNIQUE\s+constraint\s+failed:\s+(?P<columns>.+)$",
        r"^.*PRIMARY\s+KEY\s+must\s+be\s+unique.*$",
    ),
)
def _sqlite_dupe_key_error(inner_exception, match, engine_name, is_disconnect):
    columns = []
    try:
        columns = match.group("columns")
        columns = [c.split(".")[-1] for c in columns.strip().split(", ")]
    except IndexError:  # pragma: no cover
        pass

    raise DBException(columns, inner_exception)


@filters(
    "sqlite",
    sqla_exc.IntegrityError,
    r"^.*FOREIGN\sKEY\s+constraint\s+failed.*$",
)
def _sqlite_fk_error(inner_exception, match, engine_name, is_disconnect):
    raise DBException(inner_exception=inner_exception, is_fk_error=True)


def handler(context):
    """Iterate through available filters and invoke those which match.
    The first one which raises wins.
    """

    def _dialect_registries(engine):
        if engine.dialect.name in _registry:
            yield _registry[engine.dialect.name]
        if "*" in _registry:
            yield _registry["*"]

    for per_dialect in _dialect_registries(context.engine):
        for exc in (context.sqlalchemy_exception, context.original_exception):
            for super_ in exc.__class__.__mro__:
                if super_ in per_dialect:
                    regexp_reg = per_dialect[super_]
                    for fn, regexp in regexp_reg:
                        match = regexp.match(exc.args[0])
                        if match:
                            fn(
                                exc,
                                match,
                                context.engine.dialect.name,
                                context.is_disconnect,
                            )
