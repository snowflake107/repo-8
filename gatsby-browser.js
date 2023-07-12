const isBrowser = typeof window !== "undefined";
export const onRouteUpdate = ({ location, prevLocation }) => {
    if (isBrowser) {
        let apiLinkLocation = window.location.href.split('#');
        if(apiLinkLocation.length > 1){
            // set the iframe to the right location
            console.log('setting iframe src to: ' + document.querySelector.all('iframe')[1].src + '/' + apiLinkLocation)
            document.querySelector.all('iframe')[1].src = document.querySelector.all('iframe')[1].src + '/' + apiLinkLocation;
        }
    }
}