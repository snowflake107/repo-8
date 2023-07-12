const isBrowser = typeof window !== "undefined";
export const onRouteUpdate = ({ location, prevLocation }) => {
    if (isBrowser) {
        let apiLinkLocation = window.location.href.split('#');
        if(apiLinkLocation.length > 1){
            window.onload = function () {
                // set the iframe to the right location
                if (window.location.host.indexOf("adobe.com") >= 0 || window.location.host.indexOf("github.io") >= 0) {
                    console.log('setting iframe src to: ' + document.querySelectorAll('iframe')[1].src + '#' + apiLinkLocation[1])
                    document.querySelectorAll('iframe')[1].src = document.querySelectorAll('iframe')[1].src + '#' + apiLinkLocation[1];
                } else {
                    console.log('setting iframe src to: ' + document.querySelectorAll('iframe')[0].src + '#' + apiLinkLocation[1])
                    document.querySelectorAll('iframe')[0].src = document.querySelectorAll('iframe')[0].src + '#' + apiLinkLocation[1];
                }
            }
        }
    }
}