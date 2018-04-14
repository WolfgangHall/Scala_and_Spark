if (3 == 3){
  println("3 is equal to 3")
}

val x = "hello"

if (x.endsWith("o")){
  println("The value of x ends with o")
} else {
  println("The value of x does not end with o")
}

// for loop
for(item <- List(1, 2, 3)){
  println(item)
}


// while loop
var x = 0

while (x < 5) {
  println(s"x is currently $x")
  println("x is still less than 5, adding 1 to x")
  x += 1
}


// break statement
import util.control.Breaks._

var y = 0
while (y < 10) {
  println(s"y is $y")
  y += 1
  if (y == 3) break
}
