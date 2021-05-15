class Button{
    constructor(x,y,w,h,text){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.text = text;
    }

    draw(){
        stroke(3)
        fill(255,50,50)
        ellipse(this.x, this.y, this.w, this.w)
 
    }

    onPosition(x,y){
        if(x>this.x-25&&x<this.x+25&&y>this.y-25&&y<this.y+25){
            return true;
        }
    }

    setPos(x,y){
        this.x = x;
        this.y = y;
    }

}