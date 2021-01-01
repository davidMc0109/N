package basic.fp16

import chisel3._
import chisel3.iotesters.{Driver, ChiselFlatSpec, PeekPokeTester}

class FP16MulTests(c: FP16Mul) extends PeekPokeTester(c){

  for(i <- FP16TestData.a.indices){
    poke(c.io.a, FP16TestData.a(i).asUInt(16.W))
    poke(c.io.b, FP16TestData.b(i).asUInt(16.W))
    step(1)
    Predef.printf("%d * %d = %d get %d\n", FP16TestData.a(i), FP16TestData.b(i), FP16TestData.m(i), peek(c.io.c))
    if((peek(c.io.c) - FP16TestData.m(i))>2 || (FP16TestData.m(i) - peek(c.io.c)>2)) {
      println("Fails")
      fail
    }
  }
}

class FP16MulTester extends ChiselFlatSpec {
  behavior of "FP16Mul"
  it should s"mul normally" in {
    Driver(() => new FP16Mul, "verilator")(c => new FP16MulTests(c)) should be (true)
  }
}


