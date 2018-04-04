package io.sdkman.repos

import io.sdkman.db.MongoConnectivity
import io.sdkman.repos
import org.mongodb.scala._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Sorts.ascending

import scala.concurrent.Future

trait CandidatesRepo {

  self: MongoConnectivity =>

  import repos.mongoExecutionContext

  def findByIdentifier(candidate: String): Future[Option[Candidate]] =
    candidatesCollection
      .find(equal("candidate", candidate))
      .first
      .map(doc => doc: Candidate)
      .toFuture()
      .map(_.headOption)

  def findAllCandidates(): Future[Seq[Candidate]] =
    candidatesCollection
      .find()
      .sort(ascending("candidate"))
      .map(doc => doc: Candidate)
      .toFuture()
}

case class Candidate(candidate: String,
                     name: String,
                     description: String,
                     default: String,
                     websiteUrl: String,
                     distribution: String)