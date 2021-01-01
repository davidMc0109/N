package fp16

import chisel3._
import chisel3.iotesters.{Driver, ChiselFlatSpec, PeekPokeTester}

class FP16AdderTests(c: FP16Adder) extends PeekPokeTester(c){
  poke(c.io.a, 13574)
  poke(c.io.b, 47499)
  expect(c.io.c, 16608)
}

class FP16AdderTester extends ChiselFlatSpec {
  behavior of "FP16Adder"
  it should s"add normally" in {
    Driver(() => new FP16Adder, "ivl")(c => new FP16AdderTests(c)) should be (true)
//    Driver.execute(
//      Array("--target-dir", "test_run_dir"),
//      () => new FP16Adder()
//    )(c => new FP16AdderTests(c)) should be (true)
  }
}

//import chisel3.tester._
//import org.scalatest.FreeSpec

//class FP16AdderSpec extends FreeSpec with ChiselScalatestTester{
//  "FP16Adder should doing adds" in {
//    test(new FP16Adder()) {dut =>
//      dut.io.a.poke(13574.U(16.W))
//      dut.io.b.poke(47499.U(16.W))
//      dut.clock.step()
//      dut.io.c.expect(16608.U(16.W))
//    }
//  }
//
//}

