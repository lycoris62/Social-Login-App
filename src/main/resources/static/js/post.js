const likeButton = document.querySelector(".like-button");

if (likeButton) {
    likeButton.addEventListener('click', () => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/post/like`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                postId: document.querySelector('.post-id').value,
                userId: document.querySelector('.user-id').value
            })
        })
            .then(() => {
                alert("등록이 완료되었습니다.");
                location.replace(`/post/${document.querySelector('.post-id').value}`);
            });
    })
}