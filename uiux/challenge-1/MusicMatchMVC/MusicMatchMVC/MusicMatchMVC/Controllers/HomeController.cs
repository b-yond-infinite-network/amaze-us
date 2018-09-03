using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Mvc;
using MusicMatchMVC.Models;
using Newtonsoft.Json;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;


namespace MusicMatchMVC.Controllers
{
    public class HomeController : Controller
    {
        public static IEnumerable<MusicMatchMVC.Models.Track> staticTrack = null;

        public ActionResult Index()
        {
            //List<Models.Artist> tm = await getdata();
            //ViewBag.tm = tm;
            return View();
        }
        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }
        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";
            return View();
        }
        [HttpGet]
        public async Task<JsonResult> FetchArtist(string getname)
        {
            try
            {
                string name = getname.Trim().ToLower();
                //string Baseurl = "https://api.musixmatch.com/ws/1.1/artist.search?format=json&q_artist='" + name + "'&apikey=eabb6c5bfbb14f41ae0a64cb9a279292";
                string Baseurl = "https://api.musixmatch.com/ws/1.1/track.search?format=json&q_artist='" + name + "'&quorum_factor=1&apikey=eabb6c5bfbb14f41ae0a64cb9a279292";

                List<Models.Track> art = new List<Models.Track>();
                using (var client = new HttpClient())
                {
                    client.BaseAddress = new Uri(Baseurl);
                    client.DefaultRequestHeaders.Clear();
                    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                    HttpResponseMessage Res = await client.GetAsync(Baseurl);
                    if (Res.IsSuccessStatusCode)
                    {
                        var EmpResponse = Res.Content.ReadAsStringAsync().Result;
                        RootObject tm = JsonConvert.DeserializeObject<RootObject>(EmpResponse);
                        var jo = JObject.Parse(EmpResponse);
                        var list = jo["message"]["body"]["track_list"].ToArray();
                        foreach (var x in tm.message.body.track_list)
                        {
                            art.Add(new Models.Track { track_id = x.track.track_id, track_name = x.track.track_name, track_length = x.track.track_length });
                        }
                    }
                    //returning the employee list to view                      
                }
                staticTrack = art.ToList();
                return Json(art, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                Console.Write(e);
                return Json("Error", JsonRequestBehavior.AllowGet);
            }
        }
        public async Task<JsonResult> FetchLyrics(long track_id)
        {
            try
            {
                long tid = track_id;
                //string Baseurl = "https://api.musixmatch.com/ws/1.1/track.lyrics.get?format=json&track_id=1234&apikey=eabb6c5bfbb14f41ae0a64cb9a279292";
                string Baseurl = "https://api.musixmatch.com/ws/1.1/track.lyrics.get?format=json&apikey=eabb6c5bfbb14f41ae0a64cb9a279292&track_id=" + track_id;
                List<Lyrics> art = new List<Lyrics>();
                using (var client = new HttpClient())
                {
                    client.BaseAddress = new Uri(Baseurl);
                    client.DefaultRequestHeaders.Clear();
                    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                    HttpResponseMessage Res = await client.GetAsync(Baseurl);
                    if (Res.IsSuccessStatusCode)
                    {
                        var EmpResponse = Res.Content.ReadAsStringAsync().Result;
                        L_RootObject tm = JsonConvert.DeserializeObject<L_RootObject>(EmpResponse);
                        var jo = JObject.Parse(EmpResponse);
                        var list = jo["message"]["body"]["lyrics"]["lyrics_body"].ToArray();
                        art.Add(new Lyrics { lyrics_body = tm.message.body.lyrics.lyrics_body });        
                    }
                    //returning the employee list to view                      
                }
                return Json(art, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                return Json("Error", JsonRequestBehavior.AllowGet);
            }
        }
    }
}