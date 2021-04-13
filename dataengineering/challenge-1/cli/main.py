import argparse
from cli.cmd.loadFromCsv import load_from_csv
from cli.cmd.generateReports import generate_reports
from cli.cmd.clearDatabase import clear_database
parser = argparse.ArgumentParser()
 
subparser = parser.add_subparsers()
printer = subparser.add_parser('load_csv', help="load_csv")
printer.set_defaults(which='load_csv')
printer.add_argument('path')

printer = subparser.add_parser('clear_db', help="clear_db")
printer.set_defaults(which='clear_db')

printer = subparser.add_parser('generate_reports', help="generate_reports")
printer.set_defaults(which='generate_reports')

#printer = subparser.add_parser('generate_stats', help="generate_stats")
#printer.set_defaults(which='generate_stats')
#printer.add_argument('path')

 
args = parser.parse_args()

if hasattr(args, 'which'):
    if args.which == 'load_csv':
        load_from_csv(args.path)
        print(args.which)
    if args.which == 'clear_db':
        clear_database()
    if args.which == 'generate_reports':
        generate_reports()
    if args.which == 'help':
        print("Help")