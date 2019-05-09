package com.task.promotion

import org.scalatest.FunSuite

class OrchestratorTest extends FunSuite {

  test("one.promotion") {
    val allPromotions = AllPromotions(Seq(Promotion("P1", null)))
    assert(Orchestrator.combinablePromotions(allPromotions.promotions.head.promotion, allPromotions.promotions).size === 0)
    assert(Orchestrator.allCombinablePromotions(allPromotions).size === 0)
  }

  test("promotion.no.combinations.available") {
    val allPromotions = AllPromotions(Seq(Promotion("P1", Seq("P2")), Promotion("P2", Seq("P1"))))
    assert(Orchestrator.combinablePromotions(allPromotions.promotions.head.promotion, allPromotions.promotions).size === 0)
    assert(Orchestrator.allCombinablePromotions(allPromotions).size === 0)
  }

  test("two.compatible.promotions") {
    val allPromotions = AllPromotions(Seq(Promotion("P1", null), Promotion("P2", null)))
    verify(Orchestrator.combinablePromotions(allPromotions.promotions.head.promotion, allPromotions.promotions), Seq(Seq("P1", "P2")), 1)

    verify(Orchestrator.combinablePromotions(allPromotions.promotions(1).promotion, allPromotions.promotions), Seq(Seq("P1", "P2")), 1)

    verify(Orchestrator.allCombinablePromotions(allPromotions), Seq(Seq("P1", "P2")), 1)
  }

  test("compatible.promotions.with.first.has.one.incompatible") {
    val promotion1 = Promotion("P1", Seq("P3"))
    val promotion2 = Promotion("P2", Seq("P4", "P5"))
    val promotion3 = Promotion("P3", Seq("P1"))
    val promotion4 = Promotion("P4", Seq("P2"))
    val promotion5 = Promotion("P5", Seq("P2"))
    val allPromotions = AllPromotions(Seq(promotion1, promotion2, promotion3, promotion4, promotion5))
    verify(Orchestrator.combinablePromotions(promotion1.promotion, allPromotions.promotions), Seq(Seq("P1", "P2"), Seq("P1", "P4", "P5")), 2)
    verify(Orchestrator.combinablePromotions(promotion2.promotion, allPromotions.promotions), Seq(Seq("P1", "P2"), Seq("P2", "P3")), 2)
    verify(Orchestrator.combinablePromotions(promotion3.promotion, allPromotions.promotions), Seq(Seq("P2", "P3"), Seq("P3", "P4", "P5")), 2)
    verify(Orchestrator.combinablePromotions(promotion4.promotion, allPromotions.promotions), Seq(Seq("P1", "P4", "P5"), Seq("P3", "P4", "P5")), 2)
    verify(Orchestrator.combinablePromotions(promotion5.promotion, allPromotions.promotions), Seq(Seq("P1", "P4", "P5"), Seq("P3", "P4", "P5")), 2)
    verify(Orchestrator.allCombinablePromotions(allPromotions), Seq(Seq("P1", "P2"), Seq("P1", "P4", "P5"), Seq("P2", "P3"), Seq("P3", "P4", "P5")), 4)
  }

  test("compatible.promotions.with.first.has.two.incompatible") {
    val promotion1 = Promotion("P1", Seq("P4", "P5"))
    val promotion2 = Promotion("P2", Seq("P3"))
    val promotion3 = Promotion("P3", Seq("P2"))
    val promotion4 = Promotion("P4", Seq("P1"))
    val promotion5 = Promotion("P5", Seq("P1"))
    val allPromotions = AllPromotions(Seq(promotion1, promotion2, promotion3, promotion4, promotion5))
    verify(Orchestrator.combinablePromotions(promotion1.promotion, allPromotions.promotions), Seq(Seq("P1", "P2"), Seq("P1", "P3")), 2)
    verify(Orchestrator.combinablePromotions(promotion2.promotion, allPromotions.promotions), Seq(Seq("P1", "P2"), Seq("P2", "P4", "P5")), 2)
    verify(Orchestrator.combinablePromotions(promotion3.promotion, allPromotions.promotions), Seq(Seq("P1", "P3"), Seq("P3", "P4", "P5")), 2)
    verify(Orchestrator.combinablePromotions(promotion4.promotion, allPromotions.promotions), Seq(Seq("P2", "P4", "P5"), Seq("P3", "P4", "P5")), 2)
    verify(Orchestrator.combinablePromotions(promotion5.promotion, allPromotions.promotions), Seq(Seq("P2", "P4", "P5"), Seq("P3", "P4", "P5")), 2)
    verify(Orchestrator.allCombinablePromotions(allPromotions), Seq(Seq("P1", "P2"), Seq("P1", "P3"), Seq("P2", "P4", "P5"), Seq("P3", "P4", "P5")), 4)
  }

  test("compatible.promotions.with.multiple.has.multiple.incompatible") {
    val promotion1 = Promotion("P1", Seq("P2", "P3", "P4"))
    val promotion2 = Promotion("P2", Seq("P1"))
    val promotion3 = Promotion("P3", Seq("P1", "P5"))
    val promotion4 = Promotion("P4", Seq("P1"))
    val promotion5 = Promotion("P5", Seq("P3", "P7"))
    val promotion6 = Promotion("P6", null)
    val promotion7 = Promotion("P7", Seq("P5"))
    val allPromotions = AllPromotions(Seq(promotion1, promotion2, promotion3, promotion4, promotion5, promotion6, promotion7))
    verify(Orchestrator.combinablePromotions(promotion1.promotion, allPromotions.promotions), Seq(Seq("P1", "P5", "P6"), Seq("P1", "P6", "P7")), 2)
    verify(Orchestrator.combinablePromotions(promotion2.promotion, allPromotions.promotions), Seq(Seq("P2", "P3", "P4", "P6", "P7"), Seq("P2", "P4", "P5", "P6")), 2)
    verify(Orchestrator.combinablePromotions(promotion3.promotion, allPromotions.promotions), Seq(Seq("P2", "P3", "P4", "P6", "P7")), 1)
    verify(Orchestrator.combinablePromotions(promotion4.promotion, allPromotions.promotions), Seq(Seq("P2", "P3", "P4", "P6", "P7"), Seq("P2", "P4", "P5", "P6")), 2)
    verify(Orchestrator.combinablePromotions(promotion5.promotion, allPromotions.promotions), Seq(Seq("P1", "P5", "P6"), Seq("P2", "P4", "P5", "P6")), 2)
    verify(Orchestrator.combinablePromotions(promotion6.promotion, allPromotions.promotions), Seq(Seq("P1", "P5", "P6"), Seq("P1", "P6", "P7"), Seq("P2", "P3", "P4", "P6", "P7"), Seq("P2", "P4", "P5", "P6")), 4)
    verify(Orchestrator.combinablePromotions(promotion7.promotion, allPromotions.promotions), Seq(Seq("P1", "P6", "P7"), Seq("P2", "P3", "P4", "P6", "P7")), 2)
    verify(Orchestrator.allCombinablePromotions(allPromotions), Seq(Seq("P1", "P5", "P6"), Seq("P1", "P6", "P7"), Seq("P2", "P3", "P4", "P6", "P7"), Seq("P2", "P4", "P5", "P6")), 4)
  }


  private def verify(promotionCombos: Seq[PromotionCombo], expectedPromotionCombos: Seq[Seq[String]], size: Integer): Unit = {
    assert(promotionCombos.size === size)
    expectedPromotionCombos.indices.foreach(index => {
      val result = promotionCombos(index).promotionCodes.toSet == expectedPromotionCombos(index).toSet
      if (!result) {
        println(s"Incorrect combination: ${promotionCombos(index).promotionCodes} and ${expectedPromotionCombos(index)}")
      }

      assert(result === true)
    })

  }

}
