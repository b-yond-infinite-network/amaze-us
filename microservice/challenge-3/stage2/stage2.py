from urllib2 import Request, urlopen, URLError

request = Request('http://localhost.booster/')

try:
    response = urlopen(request)
    booster = response.read()
    print booster

except URLError, e:
    print 'Booster still has fuel, not released', e

