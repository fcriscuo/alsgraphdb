//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.11 at 06:16:20 PM PDT 
//
package org.biographdb.alsdb.model.pubmed

import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.*
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.*
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.AuthorList.Author
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.GrantList.Grant
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.ISSN
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.JournalIssue
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.JournalIssue.PubDate
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.PublicationTypeList.PublicationType
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.ChemicalList.Chemical
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.ChemicalList.Chemical.NameOfSubstance
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.CommentsCorrectionsList.CommentsCorrections
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.MeshHeadingList.MeshHeading
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.MeshHeadingList.MeshHeading.DescriptorName
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.PubmedData
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.PubmedData.History.PubMedPubDate
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.PubmedData.ReferenceList
import javax.xml.bind.annotation.XmlRegistry

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the org.biographdb.alsdb.model.pubmed package.
 *
 * An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
class ObjectFactory
/**
 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.biographdb.alsdb.model.pubmed
 *
 */
{
    /**
     * Create an instance of [PubmedArticleSet]
     *
     */
    fun createPubmedArticleSet(): PubmedArticleSet {
        return PubmedArticleSet()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle]
     *
     */
    fun createPubmedArticleSetPubmedArticle(): PubmedArticle {
        return PubmedArticle()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedData(): PubmedData {
        return PubmedData()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.ReferenceList]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataReferenceList(): ReferenceList {
        return ReferenceList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.ReferenceList.Reference]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataReferenceListReference(): ReferenceList.Reference {
        return ReferenceList.Reference()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.ReferenceList.Reference.ArticleIdList]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataReferenceListReferenceArticleIdList(): ReferenceList.Reference.ArticleIdList {
        return ReferenceList.Reference.ArticleIdList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.ArticleIdList]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataArticleIdList(): PubmedData.ArticleIdList {
        return PubmedData.ArticleIdList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.History]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataHistory(): PubmedData.History {
        return PubmedData.History()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitation(): MedlineCitation {
        return MedlineCitation()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.MeshHeadingList]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationMeshHeadingList(): MeshHeadingList {
        return MeshHeadingList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.MeshHeadingList.MeshHeading]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationMeshHeadingListMeshHeading(): MeshHeading {
        return MeshHeading()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.CommentsCorrectionsList]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationCommentsCorrectionsList(): CommentsCorrectionsList {
        return CommentsCorrectionsList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.ChemicalList]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationChemicalList(): ChemicalList {
        return ChemicalList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.ChemicalList.Chemical]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationChemicalListChemical(): Chemical {
        return Chemical()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticle(): Article {
        return Article()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.PublicationTypeList]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticlePublicationTypeList(): PublicationTypeList {
        return PublicationTypeList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.GrantList]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleGrantList(): GrantList {
        return GrantList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.AuthorList]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleAuthorList(): AuthorList {
        return AuthorList()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleJournal(): Journal {
        return Journal()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.JournalIssue]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleJournalJournalIssue(): JournalIssue {
        return JournalIssue()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.ReferenceList.Reference.ArticleIdList.ArticleId]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataReferenceListReferenceArticleIdListArticleId(): ReferenceList.Reference.ArticleIdList.ArticleId {
        return ReferenceList.Reference.ArticleIdList.ArticleId()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.ArticleIdList.ArticleId]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataArticleIdListArticleId(): PubmedData.ArticleIdList.ArticleId {
        return PubmedData.ArticleIdList.ArticleId()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.PubmedData.History.PubMedPubDate]
     *
     */
    fun createPubmedArticleSetPubmedArticlePubmedDataHistoryPubMedPubDate(): PubMedPubDate {
        return PubMedPubDate()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.PMID]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationPMID(): PMID {
        return PMID()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.DateCompleted]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationDateCompleted(): DateCompleted {
        return DateCompleted()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.DateRevised]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationDateRevised(): DateRevised {
        return DateRevised()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.MedlineJournalInfo]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationMedlineJournalInfo(): MedlineJournalInfo {
        return MedlineJournalInfo()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.MeshHeadingList.MeshHeading.DescriptorName]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationMeshHeadingListMeshHeadingDescriptorName(): DescriptorName {
        return DescriptorName()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.CommentsCorrectionsList.CommentsCorrections]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationCommentsCorrectionsListCommentsCorrections(): CommentsCorrections {
        return CommentsCorrections()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.ChemicalList.Chemical.NameOfSubstance]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationChemicalListChemicalNameOfSubstance(): NameOfSubstance {
        return NameOfSubstance()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Pagination]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticlePagination(): Pagination {
        return Pagination()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Abstract]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleAbstract(): Abstract {
        return Abstract()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.PublicationTypeList.PublicationType]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticlePublicationTypeListPublicationType(): PublicationType {
        return PublicationType()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.GrantList.Grant]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleGrantListGrant(): Grant {
        return Grant()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.AuthorList.Author]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleAuthorListAuthor(): Author {
        return Author()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.ISSN]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleJournalISSN(): ISSN {
        return ISSN()
    }

    /**
     * Create an instance of [PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.JournalIssue.PubDate]
     *
     */
    fun createPubmedArticleSetPubmedArticleMedlineCitationArticleJournalJournalIssuePubDate(): PubDate {
        return PubDate()
    }
}