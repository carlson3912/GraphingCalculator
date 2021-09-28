//variables
var scale;
var intro = "Graphing Calculator, by Jack Carlson and James Donn. Hit (Go) when you ready to graph a function. You can change the scale of your graph in the text input, but we recommend that you use modest numbers. After writing your function, you can find out more about it by clicking (Advanced Details). This screen is not operational. ";
var i = 0;


//functions
function drawGrid(q) {      // draws grid, according to scale set by user (q)
  var z = 0;        
  var w = 0;
  console.log("drawGrid()");
 for (var e = 0; e < q; e++) {
   for (var i = 0; i < q; i ++) {
      rect(z, w, 320 / q, 320 / q);
      z = z + 320/q;
    }
   w = w + 320/q;
   z = 0;
 }
}
function drawAxis() {       //draws x and y axis in the middle of the screen
setStrokeColor("black");
setStrokeWidth(1);
 line(0, 160, 320, 160);
  line(160, 0, 160, 320);
}

//events
//getting grid
onEvent("graph", "click", function() {  //builds canvas
  createCanvas("canvas", 320, 320);             
  setStrokeWidth(0.5); 
  scale = 2 * Math.round(getText("text_input1") / 2); //gets scale from user and rounds it to even number
  canvasInit();
  drawFunc();
 hideElement("graph");
  showElement("Reset");
}); 

function canvasInit() {
  drawGrid(scale);
  drawAxis();
}

function drawFunc() {
  setStrokeWidth(1.5);        // this plots each pixel on the graph
  setStrokeColor(getText("dropdown1"));
  var x = -1/2*scale;
  var nx = -159/320*scale;      //math to calculate where to place pixel
  var y = getY(x);
  var ny = getY(nx);
  for(var i = 0; i < 320; i++) {
    x = nx;
    nx = (i - 159)/160*scale/2;
    y = ny;
    ny = getY(nx);
    line(i, y, i+1, ny);    //where its drawn
  }
}

function getY(x) {  //this calculates the values for the various points in the graph
  return 160 - (getNumber("cubed")*Math.pow(x, 3) + getNumber("squared")*Math.pow(x, 2) + getNumber("one")*x + getNumber("constant"))*320/scale;
}

//code that runs once
hideElement("Reset");
console.log(intro.length);
timedLoop(10, function() {
  setText("text_area1", intro.substring(0, i));
  i++;
});


function drawPixel(x, y) {
  line(x, y, x, y);
}

//Advanced details section
onEvent("button12", "click", function() {
  setScreen("screen1");
setText("text_input1", 40);
  setText("cubed", "0");
  setText("squared", "0");
  setText("one", "0");
  setText("constant", "0");
});

onEvent("adbtn", "click", function() {    //moves to advanced details
  setScreen("adscrn");
});
onEvent("back", "click", function() {   //returns to graphing screen
   setScreen("screen1");
});
onEvent("button4", "click", function() {  //returns to graphing
  setScreen("adscrn");
});
onEvent("button1", "click", function() {    //brings user to y value calculator
  setScreen("yvalue");
});

//Finding the specific y-value
function specificX(p) {       //really simple, just calculates the Y value given x
   return (getNumber("cubed")*Math.pow(p, 3) + getNumber("squared")*Math.pow(p, 2) + getNumber("one")*p + getNumber("constant"));
}
onEvent("button3", "click", function() {
  setText("label8", specificX(getNumber("text_input2")));
  
});

//Differentiation Section
onEvent("button2", "click", function() {
  setScreen("calc");
});
onEvent("button5", "click", function() {
  setScreen("adscrn");
});
function calcSlope(x) {
 return (getNumber("cubed")*Math.pow(x, 2) * 3 + 2 * getNumber("squared")*Math.pow(x, 1) + getNumber("one")); //finds the derivative and then plugs in x
}
onEvent("findslope", "click", function() {
 setText("label9", calcSlope(getNumber("text_input3"))); 
});

//Y-intercept Section
onEvent("button7", "click", function() {
  setScreen("y-intercept");
  setText("label11", specificX(0));
});
onEvent("button9", "click", function() {
  setScreen("adscrn");
});

//Integration Section
onEvent("button8", "click", function() {
  setScreen("Integrals");

});
onEvent("button10", "click", function() {
  setScreen("adscrn");
  
});
onEvent("button11", "click", function() {
  setText("label16", integral(getText("text_input4")) - integral (getText("text_input5")));
});
function integral(c)  {
  return 0.01 * Math.round(100 * (getNumber("cubed")*Math.pow(c, 4) * 0.25 + 0.33 * getNumber("squared")*Math.pow(c, 3) + 0.5 * getNumber("one")*Math.pow(c, 2) + getNumber("constant") * c)); //finds the integral and then plugs in the value of X
}

onEvent("Reset", "click", function() {
  deleteElement("canvas");
  hideElement("Reset");
  showElement("graph");
});
onEvent("ctog", "click", function() {
  setScreen("screen1");
});
onEvent("gtoc", "click", function() {
  setScreen("normal");
});
//normal calculator
var numlist = [];

onEvent("plus", "click", function() {
appendItem(numlist, getText("text_input6"));
appendItem(numlist, " + "); //Adds number written plus sign into the list
console.log(numlist);
setText("equation", numlist);
setText("text_input6", "");
});

onEvent("minus", "click", function() {
appendItem(numlist, getText("text_input6"));
appendItem(numlist, " - "); //Adds number written plus sign into the list
console.log(numlist);
setText("equation", numlist);
setText("text_input6", "");
});

onEvent("Multiply", "click", function() {
appendItem(numlist, getText("text_input6"));
appendItem(numlist, " x "); //Adds number written plus sign into the list
console.log(numlist);
setText("equation", numlist);
setText("text_input6", "");
});

onEvent("divide", "click", function() {
appendItem(numlist, getText("text_input6"));
appendItem(numlist, " / "); //Adds number written plus sign into the list
console.log(numlist);
setText("equation", numlist);
setText("text_input6", "");
});

//Calculations for Normal Calculaotr
onEvent("equals", "click", function() {
appendItem(numlist, getText("text_input6"));
appendItem(numlist, " = "); //Adds number written plus sign into the list
setText("equation", numlist);
  write (".");
  write (".");
timedLoop(50, function() {
  if (numlist.indexOf(" / ") > -1) { //because of PEMDAS, we need to do multiplication and division before addition and subtraction
    var xpos;
    var part;
    xpos = numlist.indexOf(" / "); //finds the postion of the division sign
    console.log(xpos);
    part =   numlist[xpos - 1] / numlist[(xpos + 1)]; //takes the item in front of it and divides it by the number after the division sign, set their value to "part"
    console.log(part);
    removeItem(numlist, xpos + 1);
    removeItem(numlist, xpos);
    removeItem(numlist, xpos - 1); //removes the sign and both numbers around it
    insertItem(numlist, xpos - 1, part); //replaces those 3 items with "part"
    write(numlist);
  } else {
    if (numlist.indexOf(" x ") > -1) {
      var part;
      var xpos;
      xpos = numlist.indexOf(" x ");
      console.log(xpos);
      part =   numlist[xpos - 1] * numlist[(xpos + 1)];
      console.log(part);
      removeItem(numlist, xpos + 1);
      removeItem(numlist, xpos);
      removeItem(numlist, xpos - 1);
      insertItem(numlist, xpos - 1, part);
      write(numlist);
    } else {
      if (numlist.indexOf(" - ") > -1) {
      var part;
      var xpos;
      xpos = numlist.indexOf(" - ");
      console.log(xpos);
      part =   Number(numlist[Number(xpos) - Number(1)]) - Number(numlist[Number((xpos) + Number(1))]);
      console.log(part);
      removeItem(numlist, xpos + 1);
      removeItem(numlist, xpos);
      removeItem(numlist, xpos - 1);
      insertItem(numlist, xpos - 1, part);
      write(numlist);
      } else {
        if (numlist.indexOf(" + ") > -1) {
      var part;
      var xpos;
      xpos = numlist.indexOf(" + ");
      console.log(xpos);
      part =   Number(numlist[Number(xpos) - Number(1)]) + Number(numlist[Number((xpos) + Number(1))]);
      console.log(part);
      removeItem(numlist, xpos + 1);
      removeItem(numlist, xpos);
      removeItem(numlist, xpos - 1);
      insertItem(numlist, xpos - 1, part);   
        write(numlist);
        } else {
          stopTimedLoop(); //when their are no more operation signs, you have your answer
        }
      }
    }
   
  }
});
});
onEvent("Resetcalc", "click", function() {
 numlist = [];
deleteElement();
  
});
