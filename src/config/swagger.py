swagger_template = {
    'swagger': '2.0',
    'info': {
        'title': 'Schedule API',
        'description': 'API for Schedules',
        'contact': {
            'responsibleOrganization': '',
            'responsibleDeveloper': '',
            'email': 'exccomrade@gmail.com'
        },
        'termsOfService': '',
        'version': '1.0'
    },
    'basePath': '/api/v1',
    'schemes': ['http', 'https'],
    'securityDefinitions': {},
}

swagger_config = {
    'headers': [],
    'specs': [
        {
            'endpoint': 'apispec',
            'route': '/apispec.json',
            'rule_filter': lambda rule: True,
            'model_filter': lambda tag: True,
        }
    ],
    'static_url_path': '/flasgger_static',
    'swagger_ui': True,
    'specs_route': '/apidocs'
}
