package com.task.promotion

case class PromotionCombo(promotionCodes: Seq[String]) {

  def contains(candidate: Seq[String]): Boolean = {
    candidate.forall(c => promotionCodes.contains(c))
  }
}
