import CryptoJS from 'crypto-js';
import React from 'react';
import cookie from 'react-cookies';


export class Login extends React.Component{

    render() {
            return (
                <div className="Vinland" id="vLog">
                            
                <h2>Vinland Kings</h2>

                <div class="login">


                    <form>
                    <label><b>Username</b></label>
                    <input class="uname" type="text" placeholder="Enter Username" required id="nam"></input>
                    <br/>    

                    <label><b>Password</b></label>
                    
                    <input class="pass" type="password" placeholder="Enter Password" required id="pwd"></input>
                    <br/>
                    <button type="button" onClick={submitHandle} >Login</button>

                    </form>
                </div>
                <div class="container">
                    <span class="errors" id="errorM"></span>
                </div>    
                <div class="container">
                <span class="psw">Forgot a <a href="/forgotPassword" class="pswHref">password?</a></span>
                </div>
                <div class="container">
                <span class="reg">Or <a href="/register" class="regHref">register</a> and try Vinland Kings for free.</span>
                </div>               
              </div>
                )
    }
 
    
    componentDidMount(){
        if(checkSession() === true){
            toGame()
        }
        else{
            console.log("IAM UNDEFINED COOKIE")
        }
    }

}


const datalog = {};

    
function submitHandle(e) {
    var pwdObj = document.getElementById('pwd').value;
    var hashPW = CryptoJS.SHA512(pwdObj);
    var hashPass = hashPW.toString(CryptoJS.enc.Base64);
    var name = document.getElementById('nam').value.toString();
    
    if(name===""||pwdObj===""){
    
    }
    else{
        let url = `http://localhost:8080/login?name=${name}&pass=${hashPass}`
       doFetch(url).then((data) => {
           console.log(data)
           datalog.status = data["status"]
           datalog.user = data["name"]
           if(datalog.status === "ok"){
            datalog.session = data["session"]
            logUser()
            toGame()
           }
           else{
               if(data["error"] === "1057")
               {
                const nam = document.getElementById('nam')
                const pas = document.getElementById('pwd')
                nam.style = "border-style: solid;border-color: red;border-width: medium;"
                pas.style = "border-style: solid;border-color: red;border-width: medium;"
                const erM = document.getElementById('errorM')
                erM.innerText = data["message"]
                erM.style = ""
                //TODO add function that fades error borders after error after some time
                }  
           }
       }).catch(function (ex){
           console.log('Response parsing failed. Error: ', ex);
       });
    }
};


function doFetch(url){
    return fetch(url,{
        method: 'post',
            headers:{
                'Accept' : 'application/json, text/plain, */*',
                'Content-Type': 'application/json',
                },
                'credentials': 'same-origin'
            })
            .then(res=>res.json());
}

function logUser(){
    let timerino = 60 * 20; 
    const expiresSet = new Date()
    expiresSet.setDate(Date.now() + 1000 * timerino)
    cookie.remove('VINLANDSESSION')
    cookie.remove('VINLANDUSER')
    cookie.save(
        'VINLANDSESSION',
        datalog.session,
        {path: '/',expires:expiresSet,maxAge:timerino,secure:true}
    )
    cookie.save(
        'VINLANDUSER',
        datalog.user,
        {path: '/',expires:expiresSet,maxAge:timerino,secure:true}
    )    
    console.log(cookie.load('VINLANDSESSION'))
    console.log(cookie.load('VINLANDUSER'))

}

function toGame() {
    window.location.href = "http://localhost:3000/game"
}



async function checkSession(){
    const session = cookie.load('VINLANDSESSION')
    const vinusr = cookie.load('VINLANDUSER')
    if(session === undefined || vinusr === undefined){
        return false;
    }
    else{
        let url = `http://localhost:8080/session?id=${session}&name=${vinusr}`
        await doFetch(url).then((data) => {
            console.log(data)
            if(data["status" === "ok"]){
                return true;
            }
        }).catch(function (ex){
            console.log('Response parsing failed. Error: ', ex);
        });
    }  
} 


export default Login;