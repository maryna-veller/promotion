package com.task.promotion

import org.scalatest.FunSuite

class AllPromotionsTest extends FunSuite {
  test("Promotions.null") {
    intercept[IllegalArgumentException] {
      AllPromotions(null)
    }
  }

  test("Promotions.empty") {
    intercept[IllegalArgumentException] {
      AllPromotions(Seq())
    }
  }

  test("Promotions.correct") {
    val allPromotions = AllPromotions(Seq(Promotion("P1", Seq("P2")), Promotion("P2", Seq("P1"))))
    assert(allPromotions.promotionsMapped.contains("P1"))
    assert(allPromotions.promotionsMapped.contains("P2"))
  }

  test("Promotions.correct.with.multiple") {
    val allPromotions = AllPromotions(
      Seq(Promotion("P1", Seq("P2", "P3")),
        Promotion("P2", Seq("P1", "P4")),
        Promotion("P3", Seq("P1", "P5")),
        Promotion("P4", Seq("P2")),
        Promotion("P5", Seq("P3"))
      ))
    assert(allPromotions.promotionsMapped.contains("P1"))
    assert(allPromotions.promotionsMapped.contains("P2"))
    assert(allPromotions.promotionsMapped.contains("P3"))
    assert(allPromotions.promotionsMapped.contains("P4"))
    assert(allPromotions.promotionsMapped.contains("P5"))
  }

  test("Promotions.incorrect") {
    intercept[IllegalArgumentException] {
      AllPromotions(Seq(Promotion("P1", Seq("P2")), Promotion("P2", Seq("P3"))))
    }
  }


  test("Promotions.incorrect.orphan") {
    intercept[IllegalArgumentException] {
      AllPromotions(
        Seq(Promotion("P1", Seq("P2")),
          Promotion("P2", Seq("P1", "P4"))
        ))
    }
  }

  test("Promotions.incorrect.not.symmetrical.comples") {
    intercept[IllegalArgumentException] {
      AllPromotions(
        Seq(Promotion("P1", Seq("P2", "P3")),
          Promotion("P2", Seq("P1", "P4")),
          Promotion("P3", Seq("P1")),
          Promotion("P4", Seq("P2")),
          Promotion("P5", Seq("P3"))
        ))
    }
  }
}
