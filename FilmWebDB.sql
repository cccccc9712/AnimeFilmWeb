
CREATE TABLE [User] (
    userID INT PRIMARY KEY IDENTITY(1,1),
    userName NVARCHAR(255),
    userPass NVARCHAR(255),
    gmail NVARCHAR(255),
    isAdmin BIT
);
CREATE TABLE PremiumStatus (
    premiumID INT PRIMARY KEY IDENTITY(1,1),
    userID INT,
    premiumName NVARCHAR(255),
    registeredDate DATETIME,
    outOfDate DATETIME,
    FOREIGN KEY (userID) REFERENCES [User](userID)
);
CREATE TABLE Category (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
    CategoryName NVARCHAR(255)
);
CREATE TABLE Film (
    filmID INT PRIMARY KEY IDENTITY(1,1),
    filmName NVARCHAR(255),
    [description] TEXT,
    releaseDate DATETIME,
    imageLink NVARCHAR(255),
    trailerLink NVARCHAR(255),
    viewCount BIGINT
);
CREATE TABLE FilmCategory (
    filmID INT,
    CategoryID INT,
    FOREIGN KEY (filmID) REFERENCES Film(filmID),
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)
);
CREATE TABLE Tag (
    tagID INT PRIMARY KEY IDENTITY(1,1),
    tagName NVARCHAR(255)
);
CREATE TABLE FilmTag (
    filmID INT,
    tagID INT,
    FOREIGN KEY (filmID) REFERENCES Film(filmID),
    FOREIGN KEY (tagID) REFERENCES Tag(tagID)
);
CREATE TABLE Season (
    seasonID INT PRIMARY KEY IDENTITY(1,1),
    filmID INT,
    seasonName NVARCHAR(255),
    [description] TEXT,
    startDate DATETIME,
    endDate DATETIME,
    FOREIGN KEY (filmID) REFERENCES Film(filmID)
);
CREATE TABLE Episode (
    episodeID INT PRIMARY KEY IDENTITY(1,1),
    seasonID INT,
    title NVARCHAR(255),
    releaseDate DATETIME,
    FOREIGN KEY (seasonID) REFERENCES Season(seasonID)
	episodeLink NVARCHAR(255);
);


CREATE TABLE Favourite (
    userID INT,
    filmID INT,
    addedDate DATETIME,
    FOREIGN KEY (userID) REFERENCES [User](userID),
    FOREIGN KEY (filmID) REFERENCES Film(filmID)
);
CREATE TABLE WatchHistory (
    historyID INT PRIMARY KEY IDENTITY(1,1),
    userID INT,
    filmID INT,
    watchDate DATETIME,
    watchTime TIME,
    episodeID INT,
    FOREIGN KEY (userID) REFERENCES [User](userID),
    FOREIGN KEY (filmID) REFERENCES Film(filmID),
    FOREIGN KEY (episodeID) REFERENCES Episode(episodeID)
);
CREATE TABLE Comments (
    commentID INT PRIMARY KEY IDENTITY(1,1),
    filmID INT,
    userID INT,
    commentText TEXT,
    commentDate DATETIME,
    parentCommentID INT,
    FOREIGN KEY (filmID) REFERENCES Film(filmID),
    FOREIGN KEY (userID) REFERENCES [User](userID),
    FOREIGN KEY (parentCommentID) REFERENCES Comments(commentID)
);
CREATE TABLE Ratings (
    ratingID INT PRIMARY KEY IDENTITY(1,1),
    filmID INT,
    userID INT,
    ratingValue FLOAT,
    ratingDate DATETIME,
    FOREIGN KEY (filmID) REFERENCES Film(filmID),
    FOREIGN KEY (userID) REFERENCES [User](userID)
);

