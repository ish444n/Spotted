
function toProfile() {
	fetchFile('profile.html');
	showBackButton(true);
}

async function toCreate() {
  try {
    await fetchFile('create.html');
    console.log('fetch operation completed');

    loadPage();
    showBackButton(true);
  } catch (error) {
    console.error('Error in toCreate:', error.message);
  }
}


function goBack(){
}
