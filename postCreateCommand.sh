pip install -r requirements.txt
pip install -r ./dev_tools/requirements.txt
python -m dev_tools.run init-database 
python -m dev_tools.run rotate-jwt-secret