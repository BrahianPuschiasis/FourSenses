window.addEventListener('load', function () {

const formulario = document.querySelector('#add_new_comida');

formulario.addEventListener('submit', async function (event) {
  event.preventDefault(); // Prevenir el envío del formulario

  const nombre = document.querySelector('#nombre').value;
  const descripcion = document.querySelector('#descripcion').value;
  const categoria = document.querySelector('#categoria').value;
  const imagenes = document.querySelector('#imagenes').value.split(',').map(url => url.trim()); // Dividir y limpiar los enlaces

  const formData = {
    nombre,
    descripcion,
    categoria,
    imagenes,
  };

  try {
    const url = '/comidas/guardar';
    const settings = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    };

    const response = await fetch(url, settings);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();

    let successAlert =
      '<div class="alert alert-success alert-dismissible">' +
      '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
      '<strong>Comida agregada</strong></div>';

    document.querySelector('#response').innerHTML = successAlert;
    document.querySelector('#response').style.display = 'block';
    resetUploadForm();
  } catch (error) {
    let errorAlert =
      '<div class="alert alert-danger alert-dismissible">' +
      '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
      `<strong>Error: ${error.message}</strong></div>`;

    document.querySelector('#response').innerHTML = errorAlert;
    document.querySelector('#response').style.display = 'block';
    resetUploadForm();
  }
});

function resetUploadForm() {
  document.querySelector('#nombre').value = '';
  document.querySelector('#descripcion').value = '';
  document.querySelector('#categoria').value = 'Desayunos y Brunch'; // Establecer valor predeterminado
  document.querySelector('#imagenes').value = '';
}

// Resto del código...

  (function () {
    let pathname = window.location.pathname;
    if (pathname === '/') {
      document.querySelector('.nav .nav-item a:first').classList.add('active');
    } else if (pathname == '/comidasList.html') {
      document.querySelector('.nav .nav-item a:last').classList.add('active');
    }
  })();
});

