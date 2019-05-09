package com.task.promotion

import org.scalatest.FunSuite

class PromotionTest extends FunSuite {
  test("Promotion.applicable") {
    val existing = Promotion("P1", Seq("P2"))
    assert(existing.applicable("P3"))
  }

  test("Promotion.applicable.empty.exclusion") {
    val existing = Promotion("P1", null)
    assert(existing.applicable("P3"))
  }

  test("Promotion.applicable.empty.exclusion.second") {
    val existing = Promotion("P1", Seq("P2"))
    assert(existing.applicable("P3"))
  }


  test("Promotion.not.applicable") {
    val existing = Promotion("P1", Seq("P2"))
    assert(existing.applicable("P2") === false)
  }

  test("Promotion.null") {
    intercept[IllegalArgumentException] {
      Promotion(null, Seq("P2"))
    }
  }

  test("Promotion.empty") {
    intercept[IllegalArgumentException] {
      Promotion("", Seq("P2"))
    }
  }

  test("Promotion.notCompatibleWith.Value.Empty") {
    intercept[IllegalArgumentException] {
      Promotion("P1", Seq(""))
    }
  }

  test("Promotion.notCompatibleWith.Value.Equal.Self") {
    intercept[IllegalArgumentException] {
      Promotion("P1", Seq("P1"))
    }
  }
}
