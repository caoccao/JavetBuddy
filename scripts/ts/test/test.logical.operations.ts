import {
  type byte,
  char,
  float,
  double,
  int,
  long,
  short,
} from "./test.type.aliases.ts";

class Test {
  public logicalEQEQ_DD_Z(a: double, b: double): boolean {
    return a === b;
  }

  public logicalEQEQ_FF_Z(a: float, b: float): boolean {
    return a === b;
  }

  public logicalEQEQ_II_Z(a: int, b: int): boolean {
    return a === b;
  }

  public logicalEQEQ_IJ_Z(a: int, b: long): boolean {
    return a === b;
  }

  public logicalEQ_DD_Z(a: double, b: double): boolean {
    return a == b;
  }

  public logicalEQ_FF_Z(a: float, b: float): boolean {
    return a == b;
  }

  public logicalEQ_II_Z(a: int, b: int): boolean {
    return a == b;
  }

  public logicalEQ_IJ_Z(a: int, b: long): boolean {
    return a == b;
  }

  public logicalGE_BB_Z(a: byte, b: byte): boolean {
    return a >= b;
  }

  public logicalGE_CC_Z(a: char, b: char): boolean {
    return a >= b;
  }

  public logicalGE_DD_Z(a: double, b: double): boolean {
    return a >= b;
  }

  public logicalGE_FF_Z(a: float, b: float): boolean {
    return a >= b;
  }

  public logicalGE_II_Z(a: int, b: int): boolean {
    return a >= b;
  }

  public logicalGE_IJ_Z(a: int, b: long): boolean {
    const c: boolean = a >= b;
    return c;
  }

  public logicalGE_SS_Z(a: short, b: short): boolean {
    return a >= b;
  }

  public logicalGT_DD_Z(a: double, b: double): boolean {
    return a > b;
  }

  public logicalGT_FF_Z(a: float, b: float): boolean {
    return a > b;
  }

  public logicalGT_II_Z(a: int, b: int): boolean {
    return a > b;
  }

  public logicalGT_IJ_Z(a: int, b: long): boolean {
    return a > b;
  }

  public logicalLE_DD_Z(a: double, b: double): boolean {
    return a <= b;
  }

  public logicalLE_FF_Z(a: float, b: float): boolean {
    return a <= b;
  }

  public logicalLE_II_Z(a: int, b: int): boolean {
    return a <= b;
  }

  public logicalLE_IJ_Z(a: int, b: long): boolean {
    return a <= b;
  }

  public logicalLT_DD_Z(a: double, b: double): boolean {
    return a < b;
  }

  public logicalLT_FF_Z(a: float, b: float): boolean {
    return a < b;
  }

  public logicalLT_II_Z(a: int, b: int): boolean {
    return a < b;
  }

  public logicalLT_IJ_Z(a: int, b: long): boolean {
    return a < b;
  }

  public logicalNotEQEQ_DD_Z(a: double, b: double): boolean {
    return a !== b;
  }

  public logicalNotEQEQ_FF_Z(a: float, b: float): boolean {
    return a !== b;
  }

  public logicalNotEQEQ_II_Z(a: int, b: int): boolean {
    return a !== b;
  }

  public logicalNotEQEQ_IJ_Z(a: int, b: long): boolean {
    return a !== b;
  }

  public logicalNotEQ_DD_Z(a: double, b: double): boolean {
    return a != b;
  }

  public logicalNotEQ_FF_Z(a: float, b: float): boolean {
    return a != b;
  }

  public logicalNotEQ_II_Z(a: int, b: int): boolean {
    return a != b;
  }

  public logicalNotEQ_IJ_Z(a: int, b: long): boolean {
    return a != b;
  }

  public logicalNot_EQEQ_DD_Z(a: double, b: double): boolean {
    return !(a === b);
  }

  public logicalNot_EQEQ_FF_Z(a: float, b: float): boolean {
    return !(a === b);
  }

  public logicalNot_EQEQ_II_Z(a: int, b: int): boolean {
    return !(a === b);
  }

  public logicalNot_EQEQ_IJ_Z(a: int, b: long): boolean {
    return !(a === b);
  }

  public logicalNot_EQ_DD_Z(a: double, b: double): boolean {
    return !(a == b);
  }

  public logicalNot_EQ_FF_Z(a: float, b: float): boolean {
    return !(a == b);
  }

  public logicalNot_EQ_II_Z(a: int, b: int): boolean {
    return !(a == b);
  }

  public logicalNot_EQ_IJ_Z(a: int, b: long): boolean {
    return !(a == b);
  }

  public logicalNot_GE_DD_Z(a: double, b: double): boolean {
    return !(a >= b);
  }

  public logicalNot_GE_FF_Z(a: float, b: float): boolean {
    return !(a >= b);
  }

  public logicalNot_GE_II_Z(a: int, b: int): boolean {
    return !(a >= b);
  }

  public logicalNot_GE_IJ_Z(a: int, b: long): boolean {
    return !(a >= b);
  }

  public logicalNot_GT_DD_Z(a: double, b: double): boolean {
    return !(a > b);
  }

  public logicalNot_GT_FF_Z(a: float, b: float): boolean {
    return !(a > b);
  }

  public logicalNot_GT_II_Z(a: int, b: int): boolean {
    return !(a > b);
  }

  public logicalNot_GT_IJ_Z(a: int, b: long): boolean {
    return !(a > b);
  }

  public logicalNot_JE_DD_Z(a: double, b: double): boolean {
    return !(a <= b);
  }

  public logicalNot_JE_FF_Z(a: float, b: float): boolean {
    return !(a <= b);
  }

  public logicalNot_JE_II_Z(a: int, b: int): boolean {
    return !(a <= b);
  }

  public logicalNot_JE_IJ_Z(a: int, b: long): boolean {
    return !(a <= b);
  }

  public logicalNot_JT_DD_Z(a: double, b: double): boolean {
    return !(a < b);
  }

  public logicalNot_JT_FF_Z(a: float, b: float): boolean {
    return !(a < b);
  }

  public logicalNot_JT_II_Z(a: int, b: int): boolean {
    return !(a < b);
  }

  public logicalNot_JT_IJ_Z(a: int, b: long): boolean {
    return !(a < b);
  }

  // public logicalOr_II_Z(a: int, b: int): boolean {
  //   return (a > 0) || (b > 0);
  // }
}

console.log(new Test().logicalEQEQ_DD_Z(1, 2));
console.log(new Test().logicalEQEQ_FF_Z(1, 2));
console.log(new Test().logicalEQEQ_II_Z(1, 2));
console.log(new Test().logicalEQEQ_IJ_Z(1, 2));
console.log(new Test().logicalEQ_DD_Z(1, 2));
console.log(new Test().logicalEQ_FF_Z(1, 2));
console.log(new Test().logicalEQ_II_Z(1, 2));
console.log(new Test().logicalEQ_IJ_Z(1, 2));
console.log(new Test().logicalGE_BB_Z(1, 2));
console.log(new Test().logicalGE_CC_Z(1, 2));
console.log(new Test().logicalGE_DD_Z(1, 2));
console.log(new Test().logicalGE_FF_Z(1, 2));
console.log(new Test().logicalGE_II_Z(1, 2));
console.log(new Test().logicalGE_IJ_Z(1, 2));
console.log(new Test().logicalGE_SS_Z(1, 2));
console.log(new Test().logicalGT_DD_Z(1, 2));
console.log(new Test().logicalGT_FF_Z(1, 2));
console.log(new Test().logicalGT_II_Z(1, 2));
console.log(new Test().logicalGT_IJ_Z(1, 2));
console.log(new Test().logicalLE_DD_Z(1, 2));
console.log(new Test().logicalLE_FF_Z(1, 2));
console.log(new Test().logicalLE_II_Z(1, 2));
console.log(new Test().logicalLE_IJ_Z(1, 2));
console.log(new Test().logicalLT_DD_Z(1, 2));
console.log(new Test().logicalLT_FF_Z(1, 2));
console.log(new Test().logicalLT_II_Z(1, 2));
console.log(new Test().logicalLT_IJ_Z(1, 2));
console.log(new Test().logicalNotEQEQ_DD_Z(1, 2));
console.log(new Test().logicalNotEQEQ_FF_Z(1, 2));
console.log(new Test().logicalNotEQEQ_II_Z(1, 2));
console.log(new Test().logicalNotEQEQ_IJ_Z(1, 2));
console.log(new Test().logicalNotEQ_DD_Z(1, 2));
console.log(new Test().logicalNotEQ_FF_Z(1, 2));
console.log(new Test().logicalNotEQ_II_Z(1, 2));
console.log(new Test().logicalNotEQ_IJ_Z(1, 2));
console.log(new Test().logicalNot_EQEQ_DD_Z(1, 2));
console.log(new Test().logicalNot_EQEQ_FF_Z(1, 2));
console.log(new Test().logicalNot_EQEQ_II_Z(1, 2));
console.log(new Test().logicalNot_EQEQ_IJ_Z(1, 2));
console.log(new Test().logicalNot_EQ_DD_Z(1, 2));
console.log(new Test().logicalNot_EQ_FF_Z(1, 2));
console.log(new Test().logicalNot_EQ_II_Z(1, 2));
console.log(new Test().logicalNot_EQ_IJ_Z(1, 2));
console.log(new Test().logicalNot_GE_DD_Z(1, 2));
console.log(new Test().logicalNot_GE_FF_Z(1, 2));
console.log(new Test().logicalNot_GE_II_Z(1, 2));
console.log(new Test().logicalNot_GE_IJ_Z(1, 2));
console.log(new Test().logicalNot_GT_DD_Z(1, 2));
console.log(new Test().logicalNot_GT_FF_Z(1, 2));
console.log(new Test().logicalNot_GT_II_Z(1, 2));
console.log(new Test().logicalNot_GT_IJ_Z(1, 2));
console.log(new Test().logicalNot_JT_DD_Z(1, 2));
console.log(new Test().logicalNot_JT_FF_Z(1, 2));
console.log(new Test().logicalNot_JT_II_Z(1, 2));
console.log(new Test().logicalNot_JT_IJ_Z(1, 2));
console.log(new Test().logicalNot_JE_DD_Z(1, 2));
console.log(new Test().logicalNot_JE_FF_Z(1, 2));
console.log(new Test().logicalNot_JE_II_Z(1, 2));
console.log(new Test().logicalNot_JE_IJ_Z(1, 2));
// console.log(new Test().logicalOr_II_Z(1, 2));
