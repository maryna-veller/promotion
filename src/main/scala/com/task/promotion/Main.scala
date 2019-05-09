package com.task.promotion

object Main extends App {
  val promotion1 = Promotion("P1", Seq("P3"))
  val promotion2 = Promotion("P2", Seq("P4", "P5"))
  val promotion3 = Promotion("P3", Seq("P1"))
  val promotion4 = Promotion("P4", Seq("P2"))
  val promotion5 = Promotion("P5", Seq("P2"))
  val allPromotions = AllPromotions(Seq(promotion1, promotion2, promotion3, promotion4, promotion5))

  println(Orchestrator.allCombinablePromotions(allPromotions))
  println(Orchestrator.combinablePromotions(promotion1.promotion, allPromotions.promotions))
  println(Orchestrator.combinablePromotions(promotion3.promotion, allPromotions.promotions))
}
