let newBooksPage = 1;
let bestsellersPage = 1;

function loadMoreBooks(type) {
    let url = '';
    let listElement = null;

    if (type === 'new') {
        url = `/api/books/new-arrivals?page=${newBooksPage}&size=3`;
        listElement = document.getElementById('newBooksList');
        newBooksPage++;
    } else if (type === 'bestseller') {
        url = `/api/books/bestsellers?page=${bestsellersPage}&size=3`;
        listElement = document.getElementById('bestsellerList');
        bestsellersPage++;
    }

    fetch(url)
        .then(response => response.json())
        .then(books => {
            books.forEach(book => {
                const bookItem = document.createElement('div');
                bookItem.classList.add('book-item');
                bookItem.innerHTML = `<p>${book.title}</p><p>저자: ${book.author}</p>`;
                listElement.appendChild(bookItem);
            });
        });
}

function searchBooks() {
    const query = document.getElementById('search').value;
    alert('Searching for: ' + query);
}
