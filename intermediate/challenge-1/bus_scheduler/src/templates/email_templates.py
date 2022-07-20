from pathlib import Path
from jinja2 import Environment, FileSystemLoader


BASE_PATH = Path(__file__).parent.resolve()
file_loader = FileSystemLoader(f"{BASE_PATH}/html")
env = Environment(loader=file_loader)


def get_email_template(template_name: str, data: dict):
    template = env.get_template(name=template_name)
    output = template.render(data)
    return output
