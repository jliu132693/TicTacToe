import jinja2
import os

templateDir = os.path.join(os.path.dirname(__file__), 'templates')
jinjaEnv = jinja2.Environment(loader=jinja2.FileSystemLoader(templateDir), autoescape=True)


def renderStr(template, **params):
    html = jinjaEnv.get_template(template)
    return html.render(params)