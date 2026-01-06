package com.on.model.discussion.content

enum class DiscussionStatus {
    UNDEFINED,
    RECRUITING,
    RECRUITCOMPLETE,
    INDISCUSSION,
    DISCUSSIONCOMPLETE,
    ;

    companion object{
        fun of(discussionType: String): DiscussionStatus = when (discussionType) {
            "RECRUITING" -> RECRUITING
            "RECRUITCOMPLETE" -> RECRUITCOMPLETE
            "INDISCUSSION" -> INDISCUSSION
            "DISCUSSIONCOMPLETE" -> DISCUSSIONCOMPLETE
            else -> UNDEFINED
        }
    }
}
