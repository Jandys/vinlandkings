/**
 *  ============================================================
 *                  _      ____   _____ _____ _   _ 
 *                 | |    / __ \ / ____|_   _| \ | |
 *                 | |   | |  | | |  __  | | |  \| |
 *                 | |   | |  | | | |_ | | | | . ` |
 *                 | |___| |__| | |__| |_| |_| |\  |
 *                 |______\____/ \_____|_____|_| \_|                                

 * =============================================================
 * 
 * 
 */
/**
 * Half of the window
 */
let W_HALF;
let H_HALF;

let rectW = 120;
let rectH = 40;

let loginName, button;

function setup() {
  var cnv = createCanvas(windowWidth, windowHeight);
  cnv.style=('display','block');
  W_HALF = windowWidth/2;
  H_HALF = windowHeight/2;
  initMe();
}

function windowResized() {
  resizeCanvas(windowWidth, windowHeight);
  W_HALF = windowWidth/2;
  H_HALF = windowHeight/2; 
  
}

function initMe(){
  //drawLogin();
  createLogin();
}

function draw() {
  background(125);
  
}


function drawLogin(){
  textSize(map(windowWidth,480,2000,18,40))
  text("Vinland Kings",W_HALF,H_HALF)
}

function createLogin(){
  loginName = createInput();
  loginName.position('');
  loginName.class('login');

  button = createButton('submit');
  button.position('');
  button.mousePressed(logMe);
}


function logMe(){
  console.log("me");
}
