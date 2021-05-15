import React, { Component } from 'react';


export class ItemContainer extends React.Component{
    constructor(props){
        super(props)
        this.data = {
            "name":"Dagger of kings",
            "dmg":"12",
            "slot":"weapon0"
        }          
    }

    render() {
        return (
            <img src={this.getSrc()} draggable={this.props.drag} onDragStart={e=>this.handleDragStart(e,this.data)}></img>
            )
    }

    handeClick(){

    }

    handleDragStart(e,data){
        console.log(e,data);
    }

    getSrc(){
        if(this.props.img === undefined){
            return this.getPrefix("items") + 'hPXWfu.png'
        }else if(this.props.prefix === undefined){
            return this.getPrefix("items")+this.props.img
        }
        else{
            return this.getPrefix(this.props.prefix)+this.props.img
        }
    }


    getPrefix(prefix){
        switch(prefix){
            case "items":
            case "item":
                return 'http://localhost/vinland/items/';
            default:
                return "";
        }
    }
    
}




export default ItemContainer;