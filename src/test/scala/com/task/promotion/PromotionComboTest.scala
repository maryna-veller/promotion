package com.task.promotion

import org.scalatest.FunSuite

class PromotionComboTest extends FunSuite {
  val promotionCombo = PromotionCombo(Seq("P1", "P2", "P3"))
  test("contains.promotion.full.sequence") {
    assert(promotionCombo.contains(Seq("P1", "P2", "P3")))
  }

  test("contains.promotion.not.full.sequence") {
    assert(promotionCombo.contains(Seq("P1", "P3")))
  }

  test("contains.promotion.not.ordered.sequence") {
    assert(promotionCombo.contains(Seq("P3", "P2", "P1")))
  }

  test("contains.promotion.not.from.sequence") {
    assert(promotionCombo.contains(Seq("P3", "P2", "P1", "P4")) ===  false)
  }
  
  test("contains.promotion.only.not.from.sequence") {
    assert(promotionCombo.contains(Seq("P4")) ===  false)
  }
}
