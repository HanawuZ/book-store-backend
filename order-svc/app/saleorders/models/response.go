package models

type OrderSummary struct {
	TotalPrice    float64            `json:"totalPrice"`
	TotalQuantity int32              `json:"totalQuantity"`
	Items         []OrderSummaryItem `json:"items"`
}

type OrderSummaryItem struct {
	BookId          string  `json:"bookId"`
	Quantity        int32   `json:"quantity"`
	CopiesAvailable int32   `json:"copiesAvailable"`
	Genre           string  `json:"genre"`
	Isbn            string  `json:"isbn"`
	Price           float64 `json:"price"`
	PublicationYear string  `json:"publicationYear"`
	Title           string  `json:"title"`
	PublisherName   string  `json:"publisherName"`
}
