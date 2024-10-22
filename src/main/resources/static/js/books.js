document.addEventListener("DOMContentLoaded", function() {
    const bookItems = document.querySelectorAll('.book-item');

    bookItems.forEach((item) => {
        const wishlistButton = item.querySelector('.wishlist-button');
        const cartButton = item.querySelector('.cart-button');
        const buyButton = item.querySelector('.buy-button');

        wishlistButton.addEventListener('click', () => {
            alert('찜하기 기능은 아직 구현되지 않았습니다.');
        });

        cartButton.addEventListener('click', () => {
            alert('장바구니에 추가되었습니다.');
        });

        buyButton.addEventListener('click', () => {
            alert('바로구매 기능은 아직 구현되지 않았습니다.');
        });
    });
});
