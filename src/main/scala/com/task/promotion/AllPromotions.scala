package com.task.promotion

import scala.collection.SortedMap

case class AllPromotions(promotions: Seq[Promotion]) {
  require(promotions != null && promotions.nonEmpty, "at least one promotion should be available")

  val promotionsMapped = SortedMap[String, Promotion]() ++ promotions.map(p => p.promotion -> p).toMap

  require(promotionsMapped.forall(p => p._2.notCombinableWith.forall(notCompatible => verify(p._1, notCompatible))), "promotion setup is not symmetrical")

  private def verify(promotion: String, notCompatible: String) = {
    promotionsMapped.contains(notCompatible) && promotionsMapped(notCompatible).notCombinableWith.contains(promotion)
  }
}
