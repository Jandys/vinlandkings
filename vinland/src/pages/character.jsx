import React from 'react';
import { ItemContainer } from '../components/ItemContainer';



export class Character extends React.Component{



    render() {
        return (
            <div>
                <h1>This is character</h1>
                <div id="character">
                    <div>
                        <ItemContainer prefix="item" img="DdaAgr.png" id="0" drag={true}></ItemContainer>
                        <ItemContainer id="1"></ItemContainer>
                        
                    </div>



                </div>
            </div>
        )
    }






    componentDidMount(){
        //TODO check if there is loaded character files to show if not load them if they are use them
    }

}

let weaopn0 = 'http://localhost/vinland/items/DdaAgR.png'


export default Character;