import React, { Component } from 'react';


export class ImageComponent extends React.Component{
    constructor(props){
        super(props)
    }

    render() {
        return (
            <div>
                <img src={this.props.url} onClick={this.handeClick} onDrag={this.handeDrag} draggable={this.props.drag}></img>
            </div>
        )
    }

    handeClick(){

    }

    handeDrag(){

    }


}



export default ImageComponent;