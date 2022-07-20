import os
from setuptools import setup, find_packages


def read(file_name: str):
    return open(os.path.join(os.path.dirname(__file__), file_name)).read()


def read_requirements(path):
    return [
        line.strip()
        for line in read(path).split("\n")
        if not line.startswith(('"', "#", "-", "git+"))
    ]


setup(
    name="bus_schedule",
    version="1.0.0",
    author="Antonio Junior Souza Silva",
    author_email="antonio.jr.ssouza@gmail.com",
    description="A project to schedule bus drivers over the city",
    keywords="bus schedule, bus shifts",
    packages=find_packages(exclude=["tests", ".github"]),
    install_requires=read_requirements('requirements.txt'),
    python_requires=">=3.6",
    long_description=read('README.md'),
    license="BSD",
    entry_points={
        "console_scripts": ["bus_schedule = src.__main__:main"]
    },
    extras_require={"test": read_requirements("requirements-test.txt")},
    classifiers=[
        "Development Status :: 3 - Alpha",
        "Topic :: Utilities",
        "License :: OSI Approved :: BSD License",
    ],
)
