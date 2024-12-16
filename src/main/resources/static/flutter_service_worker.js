'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"assets/AssetManifest.bin": "910e7a0bc0b2cd28d7773d8628909748",
"assets/AssetManifest.bin.json": "b596855d0ab8da652a2c1401884b37c8",
"assets/AssetManifest.json": "227e014cfb293109f4e495387c8aa3e6",
"assets/FontManifest.json": "dc3d03800ccca4601324923c0b1d6d57",
"assets/fonts/MaterialIcons-Regular.otf": "dca0f732cac86cfde07728b7f3ce938b",
"assets/images/25.png": "074262e1ef0406d84dacab47c3393e7f",
"assets/images/26.png": "868ec9bceb78de0eb4c039c271de3f93",
"assets/images/46.jpg": "0edc60cdbd46089eebd238c9c9ca115c",
"assets/images/47.jpg": "3ccdd51a52370a39d64e02ab4cef8cc3",
"assets/images/6.png": "b6e098139b8d06fd5ce2cd7338bfcd79",
"assets/images/73.png": "e915b8758652f8edc9eca8e4978d938c",
"assets/images/74.png": "8e424d8a3da4cbe5cab8aa6fc90c2e1b",
"assets/images/75.png": "9c68812fa97830ebf46f7499c91956bc",
"assets/images/76.png": "54c0eed30fe36cb672323cae2752a102",
"assets/images/ACHACACHI.jpeg": "c4b7ff9fa350787239e12f23de7d9128",
"assets/images/ACHACACHI.png": "6784175200a6d8124be5339301866b33",
"assets/images/amarillo.png": "26f4788c140d384d3e6fd7343e7526e7",
"assets/images/BenedictoKara.png": "a3fe47dcec991076446eff84adbac5ae",
"assets/images/CALACOTO.JPG": "bfae07ee05d32fa6f9ad93e3f106098b",
"assets/images/CALACOTO.png": "c1d57cef0309c72b434fe6ac1006b5df",
"assets/images/CARABUCO.jpg": "46e1202a3fe9cbb2646109d79b112ec7",
"assets/images/CARABUCO.png": "006ddb4f3ce9404f96b8a0169629b2f9",
"assets/images/durazno.png": "98cbe998ac9b8431370350e520bb93e8",
"assets/images/durazno_fase1.png": "09eaec50390348713e108348bdff8a78",
"assets/images/durazno_fase2.png": "31fa7b35c37a3da784f851dcf59e90e8",
"assets/images/durazno_fase3.png": "fa2a718498b8838e0d238dd5afecc206",
"assets/images/durazno_fase4.png": "50f548799472555e4ddb6a883afe4725",
"assets/images/durazno_fase5.png": "035fc1ff28fea1781a0872a967209355",
"assets/images/durazno_fase6.png": "47bcb63d1c63e8d85b2ba6dd7893b809",
"assets/images/durazno_fase7.png": "fdfcffa9c8f228fa5f4ebfc174402266",
"assets/images/EdgarQuispe.jpeg": "e311521cebc8ebd5811c4c269b06e91e",
"assets/images/ESCOMA.jpg": "ffdb0c2e96ceb2d91f34943622f64b41",
"assets/images/ESCOMA.png": "f578d048e98d17f677044a406ca19670",
"assets/images/fondo.jpg": "8d9c218e16b0c5044b69a9d725c7df37",
"assets/images/FortunataAsquichu.png": "811855c6db04c1a4396ce35735c0c79e",
"assets/images/FranzAlejo.jpeg": "a6c5257ca8d892edb06a0f4dc3950df5",
"assets/images/hombre.png": "f1f303351f25df6047cf91770832b0d9",
"assets/images/Imagen1.jpg": "e4647e5914b8d16b562f8233d45b915c",
"assets/images/JacintoCalle.jpeg": "b6f760affb83b0f5337ed4b7d73efd64",
"assets/images/JherilMamani.png": "d17416c0bb377bcee377874fedd2bdf2",
"assets/images/logo1.jpg": "0be836a60eec4c1915098d955ee904c5",
"assets/images/logo2.jpg": "729b5cd850150d3a5857cbd762796612",
"assets/images/logo3.png": "2f4fa1dcb073d424b7c3cabb6e34d7f6",
"assets/images/logo4.png": "40d063100e3f9f1bc25822f2c1f81862",
"assets/images/LURIBAY.JPG": "d9dbff7ff7a76cd70b8541042d42bee7",
"assets/images/LURIBAY.png": "9d878434fd2156a731675964bf940619",
"assets/images/mujer.png": "0f1eb6ac61666ce9cd57500d783f826c",
"assets/images/PALCA.png": "dcbb65704061d4ecdc75093afe922646",
"assets/images/papa.png": "a800f855feca8a57bd139cdea63c31da",
"assets/images/papa_fase1.png": "7ee807383542ccffe83fdf5a5e270875",
"assets/images/papa_fase2.png": "be87a33f25085e297a3e1362587e9eec",
"assets/images/papa_fase3.png": "cb5a76469289332fad3b98538d4a1906",
"assets/images/papa_fase4.png": "b0d5ea27d4f6aed35fa1d84adcf7f881",
"assets/images/papa_fase5.png": "d12ab2d771dfdb0ba35fb2e537327524",
"assets/images/papa_fase6.png": "f3b168129dd075f36f3d9ceed22a3b40",
"assets/images/rojo.png": "fec248cdebbb2ea47cdf9e8cffcc57bd",
"assets/images/SICA%2520SICA.JPG": "8f85bf123bbc654bb7746ec7b7fa8bf9",
"assets/images/SICA%2520SICA.png": "1765d3753abf312305985129fed5d4b1",
"assets/images/TARACO.jpg": "d710be9ed429323947c54955f2caf7db",
"assets/images/TARACO.png": "eec0c4c258bbe15832bfa9d2b6134d0f",
"assets/images/verde.png": "1ca584306fe62675e5818f09dbd72d14",
"assets/NOTICES": "29314409cab5a1ba1a2f7f84c25b16fc",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "e986ebe42ef785b27164c36a9abc7818",
"assets/packages/flutter_map/lib/assets/flutter_map_logo.png": "208d63cc917af9713fc9572bd5c09362",
"assets/shaders/ink_sparkle.frag": "ecc85a2e95f5e9f53123dcaf8cb9b6ce",
"canvaskit/canvaskit.js": "c86fbd9e7b17accae76e5ad116583dc4",
"canvaskit/canvaskit.js.symbols": "38cba9233b92472a36ff011dc21c2c9f",
"canvaskit/canvaskit.wasm": "3d2a2d663e8c5111ac61a46367f751ac",
"canvaskit/chromium/canvaskit.js": "43787ac5098c648979c27c13c6f804c3",
"canvaskit/chromium/canvaskit.js.symbols": "4525682ef039faeb11f24f37436dca06",
"canvaskit/chromium/canvaskit.wasm": "f5934e694f12929ed56a671617acd254",
"canvaskit/skwasm.js": "445e9e400085faead4493be2224d95aa",
"canvaskit/skwasm.js.symbols": "741d50ffba71f89345996b0aa8426af8",
"canvaskit/skwasm.wasm": "e42815763c5d05bba43f9d0337fa7d84",
"canvaskit/skwasm.worker.js": "bfb704a6c714a75da9ef320991e88b03",
"favicon.png": "5dcef449791fa27946b3d35ad8803796",
"flutter.js": "c71a09214cb6f5f8996a531350400a9a",
"icons/Icon-192.png": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/Icon-512.png": "96e752610906ba2a93c65f8abe1645f1",
"icons/Icon-maskable-192.png": "c457ef57daa1d16f64b27b786ec2ea3c",
"icons/Icon-maskable-512.png": "301a7604d45b3e739efc881eb04896ea",
"index.html": "ee6dd53ec6f672806041ab5c258e20ee",
"/": "ee6dd53ec6f672806041ab5c258e20ee",
"main.dart.js": "06ce28e169c06d91ec9196664792c8d1",
"manifest.json": "39402164860669c53ba17a128b5cc721",
"version.json": "9d3043dd0956a62eba746f6f39a854bf"};
// The application shell files that are downloaded before a service worker can
// start.
const CORE = ["main.dart.js",
"index.html",
"assets/AssetManifest.bin.json",
"assets/FontManifest.json"];

// During install, the TEMP cache is populated with the application shell files.
self.addEventListener("install", (event) => {
  self.skipWaiting();
  return event.waitUntil(
    caches.open(TEMP).then((cache) => {
      return cache.addAll(
        CORE.map((value) => new Request(value, {'cache': 'reload'})));
    })
  );
});
// During activate, the cache is populated with the temp files downloaded in
// install. If this service worker is upgrading from one with a saved
// MANIFEST, then use this to retain unchanged resource files.
self.addEventListener("activate", function(event) {
  return event.waitUntil(async function() {
    try {
      var contentCache = await caches.open(CACHE_NAME);
      var tempCache = await caches.open(TEMP);
      var manifestCache = await caches.open(MANIFEST);
      var manifest = await manifestCache.match('manifest');
      // When there is no prior manifest, clear the entire cache.
      if (!manifest) {
        await caches.delete(CACHE_NAME);
        contentCache = await caches.open(CACHE_NAME);
        for (var request of await tempCache.keys()) {
          var response = await tempCache.match(request);
          await contentCache.put(request, response);
        }
        await caches.delete(TEMP);
        // Save the manifest to make future upgrades efficient.
        await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
        // Claim client to enable caching on first launch
        self.clients.claim();
        return;
      }
      var oldManifest = await manifest.json();
      var origin = self.location.origin;
      for (var request of await contentCache.keys()) {
        var key = request.url.substring(origin.length + 1);
        if (key == "") {
          key = "/";
        }
        // If a resource from the old manifest is not in the new cache, or if
        // the MD5 sum has changed, delete it. Otherwise the resource is left
        // in the cache and can be reused by the new service worker.
        if (!RESOURCES[key] || RESOURCES[key] != oldManifest[key]) {
          await contentCache.delete(request);
        }
      }
      // Populate the cache with the app shell TEMP files, potentially overwriting
      // cache files preserved above.
      for (var request of await tempCache.keys()) {
        var response = await tempCache.match(request);
        await contentCache.put(request, response);
      }
      await caches.delete(TEMP);
      // Save the manifest to make future upgrades efficient.
      await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
      // Claim client to enable caching on first launch
      self.clients.claim();
      return;
    } catch (err) {
      // On an unhandled exception the state of the cache cannot be guaranteed.
      console.error('Failed to upgrade service worker: ' + err);
      await caches.delete(CACHE_NAME);
      await caches.delete(TEMP);
      await caches.delete(MANIFEST);
    }
  }());
});
// The fetch handler redirects requests for RESOURCE files to the service
// worker cache.
self.addEventListener("fetch", (event) => {
  if (event.request.method !== 'GET') {
    return;
  }
  var origin = self.location.origin;
  var key = event.request.url.substring(origin.length + 1);
  // Redirect URLs to the index.html
  if (key.indexOf('?v=') != -1) {
    key = key.split('?v=')[0];
  }
  if (event.request.url == origin || event.request.url.startsWith(origin + '/#') || key == '') {
    key = '/';
  }
  // If the URL is not the RESOURCE list then return to signal that the
  // browser should take over.
  if (!RESOURCES[key]) {
    return;
  }
  // If the URL is the index.html, perform an online-first request.
  if (key == '/') {
    return onlineFirst(event);
  }
  event.respondWith(caches.open(CACHE_NAME)
    .then((cache) =>  {
      return cache.match(event.request).then((response) => {
        // Either respond with the cached resource, or perform a fetch and
        // lazily populate the cache only if the resource was successfully fetched.
        return response || fetch(event.request).then((response) => {
          if (response && Boolean(response.ok)) {
            cache.put(event.request, response.clone());
          }
          return response;
        });
      })
    })
  );
});
self.addEventListener('message', (event) => {
  // SkipWaiting can be used to immediately activate a waiting service worker.
  // This will also require a page refresh triggered by the main worker.
  if (event.data === 'skipWaiting') {
    self.skipWaiting();
    return;
  }
  if (event.data === 'downloadOffline') {
    downloadOffline();
    return;
  }
});
// Download offline will check the RESOURCES for all files not in the cache
// and populate them.
async function downloadOffline() {
  var resources = [];
  var contentCache = await caches.open(CACHE_NAME);
  var currentContent = {};
  for (var request of await contentCache.keys()) {
    var key = request.url.substring(origin.length + 1);
    if (key == "") {
      key = "/";
    }
    currentContent[key] = true;
  }
  for (var resourceKey of Object.keys(RESOURCES)) {
    if (!currentContent[resourceKey]) {
      resources.push(resourceKey);
    }
  }
  return contentCache.addAll(resources);
}
// Attempt to download the resource online before falling back to
// the offline cache.
function onlineFirst(event) {
  return event.respondWith(
    fetch(event.request).then((response) => {
      return caches.open(CACHE_NAME).then((cache) => {
        cache.put(event.request, response.clone());
        return response;
      });
    }).catch((error) => {
      return caches.open(CACHE_NAME).then((cache) => {
        return cache.match(event.request).then((response) => {
          if (response != null) {
            return response;
          }
          throw error;
        });
      });
    })
  );
}
