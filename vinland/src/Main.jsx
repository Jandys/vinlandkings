import React, {Component} from "react";

import {BrowserRouter as Router, Redirect, Route, Switch } from "react-router-dom";



//Pages
//import vinland_login from "./pages/vinland_login";
import vinland_register from "./pages/vinland_register";
import e404 from "./pages/e404";
import game from "./pages/game"
import {Login} from "./pages/vinland_login";
import {Character} from "./pages/character";

export default class Main extends Component{
    render(){
        return <Router>
            <Switch>
                <Route exact path="/" component={Login}/>
                <Route exact path="/game" component={game}/>
                <Route exact path="/register" component={vinland_register}/>
                <Route exact path="/404" component={e404}/>
                <Route exact path="/character" component={Character}/>
                <Redirect to="/404"/>
            </Switch>

        </Router>
    }
}
