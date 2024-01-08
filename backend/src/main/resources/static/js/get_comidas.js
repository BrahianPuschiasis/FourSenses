window.addEventListener('load', function () {
  (function () {

    const url = '/comidas';
    const settings = {
      method: 'GET'
    };

    fetch(url, settings)
      .then(response => response.json())
      .then(data => {
        const comidaContainer = document.getElementById('div_comida_table');

        data.forEach(comida => {
          const comidaElement = document.createElement('div');
          comidaElement.classList.add('row');

          comidaElement.innerHTML = `
            <div class="col-md-6">
              <h2>${comida.nombre}</h2>
              <div>${comida.categoria}</div>
              <p>${comida.descripcion}</p>
              <div id="imagenes"></div>
            </div>
          `;

          comidaContainer.appendChild(comidaElement);

          const imagenesDiv = comidaElement.querySelector('#imagenes');

          comida.imagenes.forEach(imagen => {
            const imgElement = document.createElement('img');
            imgElement.src = imagen; // Enlace directo a la imagen
            imgElement.classList.add('comida-img'); // Añadimos la clase para el estilo
            imagenesDiv.appendChild(imgElement);
          });
        });
      })
      .catch(error => console.error(error));
  })();

  (function () {
    let pathname = window.location.pathname;
    if (pathname == "/comidaList.html") {
      document.querySelector(".nav .nav-item:last-child a").classList.add("active");
    }
  })();
});
