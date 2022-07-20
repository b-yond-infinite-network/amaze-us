import pytest

given = pytest.mark.parametrize

@given(
    "cmd,args,msg",
    [("run", ["--help"], "--port"), ("seed-db", ["--help"], "--seed")],
)
def test_cmds_help(cli_client, cli, cmd, args, msg):
    result = cli_client.invoke(cli, [cmd, *args])
    assert result.exit_code == 0
    assert msg in result.stdout