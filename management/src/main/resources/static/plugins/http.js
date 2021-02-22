let axiosMethod = function (method, url, params, vm, cb) {
    let axiosParams = {
        method: method,
        url:  url
    };
    if ('get' === method ) {
        axiosParams.params = params;
    } else if ('post' === method ) {
        axiosParams.data = params;
    }
    axios(axiosParams).then((response) => {
        if(-99===response.data.code){
            // -99--需要登录，0-成功，-1--请求异常，-2--无权限
            if(response.data.result){
                window.location.href='/login.html'
            }else{
                cb(null, response.data);
            }
        }else{
            cb(null, response.data);
        }
    }).catch((error) => {
        cb(error, null);
    });
};

let httpGet=(url, params, vm, cb)=> {
    axiosMethod('get', url, params, vm, cb);
};
let httpPost =(url, params, vm, cb) =>{
    axiosMethod('post', url, params, vm, cb);
}