package karaoke.api

import groovy.json.JsonSlurper
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients
import org.jboss.arquillian.junit.Arquillian
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(Arquillian)
class ApiTrackTest {

    @Test
    void "should load michael jackson's tracks"() {
        def artistName = 'michael jackson'
        def url = new URIBuilder('http://localhost:8080'.toURI()
                .resolve("api/track/${URLEncoder.encode(artistName, 'UTF-8')}"))
                .addParameter('page', '1')
                .addParameter('order_by', 'artist')
                .addParameter('ascending', 'true')
                .build()
        def json = HttpClients.createDefault().execute(new HttpGet(url), { HttpResponse response ->
            int status = response.getStatusLine().getStatusCode()
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity()
                return entity ? new JsonSlurper().parse(entity.content) : null
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status)
            }
        } as ResponseHandler<String>)
        Assert.assertNotNull(json)
        Assert.assertEquals(10, json.size())
        artistName.split(' ').each { String namePart ->
            json.each {
                String artist = it.artist
                String assertMsg = "'${artist}' does not contain '${namePart}'"
                Assert.assertTrue(assertMsg, artist.toLowerCase().contains(namePart))
            }
        }
    }

}
