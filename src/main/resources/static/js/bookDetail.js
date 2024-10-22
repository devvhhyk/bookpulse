document.addEventListener("DOMContentLoaded", function() {
    const quantityInput = document.getElementById('quantity');
    const bookPriceElement = document.getElementById('bookPrice');
    const totalPriceElement = document.getElementById('totalPrice');

    // bookPrice 값은 숫자로 변환 후 사용
    const bookPrice = parseInt(bookPriceElement.textContent.replace(/[^\d]/g, ''));

    function updateTotalPrice() {
        const quantity = parseInt(quantityInput.value);
        const totalPrice = bookPrice * quantity;
        totalPriceElement.innerText = `총 상품 금액: ${totalPrice.toLocaleString()}원`;
    }

    document.getElementById('increaseBtn').addEventListener('click', function() {
        quantityInput.value = parseInt(quantityInput.value) + 1;
        updateTotalPrice();
    });

    document.getElementById('decreaseBtn').addEventListener('click', function() {
        if (parseInt(quantityInput.value) > 1) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
            updateTotalPrice();
        }
    });

    // 초기 총 상품 금액 업데이트
    updateTotalPrice();
});

function toggleWishlist() {
    const wishlistIcon = document.getElementById('wishlist-icon');
    wishlistIcon.textContent = wishlistIcon.textContent === '♡' ? '♥' : '♡';
}

function addToCart() {
    const quantity = document.getElementById('quantity').value;
    alert(`장바구니에 ${quantity}개를 담았습니다.`);
}

function buyNow() {
    const quantity = document.getElementById('quantity').value;
    alert(`바로 구매를 진행합니다. 수량: ${quantity}`);
}
