import React, { Component } from 'react';
import {ImageComponent} from './ImageComponent'



export class Character extends React.Component{
    constructor(props){
        super(props)
    }

    render() {
        return (
            <div>
                <h1>This is character</h1>
                <ImageComponent url={weaopn0} drag={true}/>
            </div>
        )
    }

    componentDidMount(){
        //TODO check if there is loaded character files to show if not load them if they are use them
    }

}

let weaopn0 = 'http://localhost/vinland/items/DdaAgR.png'


export default Character;