package basic.fp16

import chisel3._
import chisel3.stage.ChiselGeneratorAnnotation
import chisel3.util.HasBlackBoxPath
import firrtl.options.TargetDirAnnotation

class int_fp_add extends BlackBox with HasBlackBoxPath{
  val io = IO(new Bundle() {
    val mode = Input(Bits(1.W))
    val a = Input(Bits(16.W))
    val b = Input(Bits(16.W))
    val c = Output(Bits(16.W))
  })
  addPath("INT_FP_MAC/rtl/adder/adder/int_fp_add.v")
  addPath("INT_FP_MAC/rtl/adder/adder/alignment.v")
  addPath("INT_FP_MAC/rtl/adder/adder/add_normalizer.v")
  addPath("INT_FP_MAC/rtl/adder/cla/cla_nbit.v")
}

class FP16Adder extends Module {
  val io = IO(new Bundle() {
    val a = Input(Bits(16.W))
    val b = Input(Bits(16.W))
    val c = Output(Bits(16.W))
  })

  val u = Module(new int_fp_add())

  u.io.mode := 1.U(1.W)
  u.io.a    := this.io.a
  u.io.b    := this.io.b
  this.io.c := u.io.c
}

object FP16AdderTest extends App{
  new chisel3.stage.ChiselStage().execute(
    Array("-X", "verilog", "--full-stacktrace"),
    Seq(
      ChiselGeneratorAnnotation(() => new FP16Adder),
      TargetDirAnnotation("test_run_dir/FP16Adder")
    )
  )
}