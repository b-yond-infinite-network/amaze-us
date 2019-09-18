from urllib2 import Request, urlopen, URLError

request = Request('http://localhost/')

try:
    response = urlopen(request)
    booster = response.read()
    print booster

except URLError, e:
    # print urlopen(request)
    print 'Booster still has fuel, not released', e

# //python
# //pydoc
# // mention that endpoint is subject to chnage //