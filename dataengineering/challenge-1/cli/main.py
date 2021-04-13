import argparse
 
parser = argparse.ArgumentParser()
 
subparser = parser.add_subparsers()
printer = subparser.add_parser('load_csv', help="aaaaaaaaaaaaaaaaaaa")
printer.add_argument('path')
printer.add_argument('color', nargs='?')
printer.add_argument('size', type=int, nargs='?')
 
printer = subparser.add_parser('generate_stats', help="bbbbbbbbb")
printer.add_argument('path')
printer.add_argument('color', nargs='?')
printer.add_argument('size', type=int, nargs='?')
 
args = parser.parse_args()
 
print(args)