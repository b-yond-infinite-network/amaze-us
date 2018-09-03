using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(MusicMatchMVC.Startup))]
namespace MusicMatchMVC
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
