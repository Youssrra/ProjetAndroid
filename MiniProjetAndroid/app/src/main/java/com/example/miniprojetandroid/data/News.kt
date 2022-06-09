package tn.esprit.curriculumvitae.data

//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity(tableName = "news")
data class News (
   // @PrimaryKey(autoGenerate = true)
    val id: Int,

   // @ColumnInfo(name = "news_title")
    val newsTitle: String,

    //@ColumnInfo(name = "publication_date")
   // val publicationDate: String,

    //@ColumnInfo(name = "newsImage_uri")
    val newsImage: String,

    //@ColumnInfo(name = "news_description")
    val newsDescription: String
){
    constructor(
    newsTitle: String,
    newsImage: String,
    newsDescription: String
    ): this(0,newsTitle,newsImage,newsDescription)
}