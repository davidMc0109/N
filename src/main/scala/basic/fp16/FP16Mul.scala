package basic.fp16

import chisel3._
import chisel3.util.HasBlackBoxPath

class int_fp_mul extends BlackBox with HasBlackBoxPath {
  val io = IO(new Bundle() {
    val mode = Input(Bits(1.W))
    val a = Input(Bits(16.W))
    val b = Input(Bits(16.W))
    val c = Output(Bits(16.W))
    val error = Output(Bits(1.W))
  })
  addPath("INT_FP_MAC/rtl/multiplier/mul/int_fp_mul.v")
  addPath("INT_FP_MAC/rtl/multiplier/mul/mul_normalizer.v")
  addPath("INT_FP_MAC/rtl/multiplier/vedic/mul2x2.v")
  addPath("INT_FP_MAC/rtl/multiplier/vedic/mul4x4.v")
  addPath("INT_FP_MAC/rtl/multiplier/vedic/mul8x8.v")
  addPath("INT_FP_MAC/rtl/multiplier/vedic/mul16x16.v")
  addPath("INT_FP_MAC/rtl/adder/cla/cla_nbit.v")
}

class FP16Mul extends Module {
  val io = IO(new Bundle() {
    val a = Input(Bits(16.W))
    val b = Input(Bits(16.W))
    val c = Output(Bits(16.W))
    val error = Output(Bits(1.W))
  })

  val u = Module(new int_fp_mul())

  u.io.mode     := 1.U(1.W)
  u.io.a        := this.io.a
  u.io.b        := this.io.b
  this.io.c     := u.io.c
  this.io.error := u.io.error
}
