import CryptoJS from 'crypto-js';


const vinland_register = () => { 
    return(
                    <div className="Vinland">
                        
                        <h2>Vinland Kings Register</h2>
    
                        <div class="register">
    
                            <form>
                            <label><b>Username</b></label>
                            <input class="uname" type="text" placeholder="Enter Username" required id="namR"></input>
                            <br/>  
                            <label><b>Email</b></label>
                            <input class="email" type="text" placeholder="Enter Email" required id="emailR"></input>
                            <br/>  
                            <label><b>Password</b></label>
                            <input class="pass" type="password" placeholder="Enter Password" required id="pwdR"></input>
                            <br/>
                            <label><b>Password Again</b></label>
                            <input class="pass" type="password" placeholder="Enter Password Again" required id="pwd2R"></input>
                            <br/>
                            <button type="button" onClick={submitHandle}>Register</button>
                            </form>
                        </div>
                        <div class="container">
                        <span class="reg">Do you have an account?  <a href="/" class="regHref">Login here</a> and play!</span>
                        </div>

                </div>
    );
};

function submitHandle(e) {
    var namR = document.getElementById('namR').value;
    var emailR = document.getElementById('emailR').value;
    var pwdR = document.getElementById('pwdR').value;
    var pwd2R = document.getElementById('pwd2R').value;
    if(namR === "" || emailR === "" || pwdR ==="" || pwd2R ===""){

    }
    else{
        if(pwdR !== pwd2R){
            alert("Passwords must match");
        } 
        else{
            var hashPW = CryptoJS.SHA512(pwdR);
            var hashPass = hashPW.toString(CryptoJS.enc.Base64);
            
            let url = `http://localhost:8080/register?name=${namR}&pass=${hashPass}&email=${emailR}`
            doFetch(url).then((data) => {
                console.log(data)
            }).catch(function (ex){
                console.log('Response parsing failed. Error: ', ex);
            });

        }
    }
    
}

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

export default vinland_register;
