'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"assets/AssetManifest.bin": "c575e4e2b88027a818d482d713ccd745",
"assets/AssetManifest.bin.json": "aa26979fd5e958519c6da70c84686f27",
"assets/AssetManifest.json": "e93f65894a363fc6c4eca1ab5b253462",
"assets/FontManifest.json": "dc3d03800ccca4601324923c0b1d6d57",
"assets/fonts/MaterialIcons-Regular.otf": "dca0f732cac86cfde07728b7f3ce938b",
"assets/images/25.png": "a33ba1446996f255f2de0f380355ee95",
"assets/images/26.png": "8822a6ce571a088bb9145c587a644634",
"assets/images/46.jpg": "0edc60cdbd46089eebd238c9c9ca115c",
"assets/images/47.jpg": "3ccdd51a52370a39d64e02ab4cef8cc3",
"assets/images/6.png": "b6e098139b8d06fd5ce2cd7338bfcd79",
"assets/images/73.png": "e915b8758652f8edc9eca8e4978d938c",
"assets/images/74.png": "8e424d8a3da4cbe5cab8aa6fc90c2e1b",
"assets/images/75.png": "9c68812fa97830ebf46f7499c91956bc",
"assets/images/76.png": "54c0eed30fe36cb672323cae2752a102",
"assets/images/82.png": "d74d4b1dd63709ed09de52e5f5519503",
"assets/images/ACHACACHI.png": "6784175200a6d8124be5339301866b33",
"assets/images/amarillo.png": "4312d12d80443949d919e66af834655c",
"assets/images/BenedictoKara.png": "d09f0e8050cbac242b2cb5a6e9ab381b",
"assets/images/CALACOTO.png": "c1d57cef0309c72b434fe6ac1006b5df",
"assets/images/CARABUCO.png": "006ddb4f3ce9404f96b8a0169629b2f9",
"assets/images/DionisioSiles.png": "e61c51773fa2c7ee2d5dff6127945581",
"assets/images/durazno.png": "98cbe998ac9b8431370350e520bb93e8",
"assets/images/durazno_fase1.png": "ddd033a68c8fb53c39678022a5bb0a53",
"assets/images/durazno_fase2.png": "712f0037a8926f5e6bb32be25eb63fe7",
"assets/images/durazno_fase3.png": "2a55b42d989b722d299646b5fc174689",
"assets/images/durazno_fase4.png": "4d5c91789f4d50971f70e51cd3f9da14",
"assets/images/durazno_fase5.png": "c3d5a939ef98f80652a3711926812b22",
"assets/images/durazno_fase6.png": "71f1c5c37bf77fe5c6a27691f8981cf0",
"assets/images/durazno_fase7.png": "456120404d028022b85ecdc6d622b897",
"assets/images/EdgarQuispe.jpeg": "e311521cebc8ebd5811c4c269b06e91e",
"assets/images/ESCOMA.png": "f578d048e98d17f677044a406ca19670",
"assets/images/fondo.jpg": "8d9c218e16b0c5044b69a9d725c7df37",
"assets/images/FortunataAsquichu.png": "38dd38e50c0db099109d6f29bf6054bc",
"assets/images/FranzAlejo.jpeg": "a6c5257ca8d892edb06a0f4dc3950df5",
"assets/images/hombre.png": "f1f303351f25df6047cf91770832b0d9",
"assets/images/JacintoCalle.png": "ed9f42f7bd1448c7264b8ca8703f639d",
"assets/images/JherilMamani.png": "001eea1e148110ab59fa54401e8025f1",
"assets/images/JonsonQuispe.png": "7dd75c97a45a1305472913895b625285",
"assets/images/logo4.png": "50d58fb013a6cadb567609f66ed622c9",
"assets/images/LURIBAY.png": "9d878434fd2156a731675964bf940619",
"assets/images/MaritzaYujra.png": "bb7437a85a5592aaf52245a31ae811e1",
"assets/images/mujer.png": "0f1eb6ac61666ce9cd57500d783f826c",
"assets/images/PALCA.png": "dcbb65704061d4ecdc75093afe922646",
"assets/images/papa.png": "a800f855feca8a57bd139cdea63c31da",
"assets/images/papa_fase1.png": "73a5dcc75ef29f62d974e8e1f1f10b7d",
"assets/images/papa_fase2.png": "cb809b6006b961fc15789bf1ca31c65a",
"assets/images/papa_fase3.png": "5c58d3d5c200468fe57cdfb0b821f2ee",
"assets/images/papa_fase4.png": "e6972bc78f808f31b905e6eb9fde1d88",
"assets/images/papa_fase5.png": "f9c141343504669238bc9f0b857dd4fe",
"assets/images/papa_fase6.png": "8bc32ba7563afd8b460c16e9e7fcacde",
"assets/images/ReynaldoChambi.png": "6a51d36b7af85ad6f2ffb3516f80fb58",
"assets/images/rojo.png": "281817963bc22f8d8dcda96354fc61f1",
"assets/images/senamhi.png": "579ef361a6d46077ce54116e1973c58c",
"assets/images/SICA%2520SICA.png": "1765d3753abf312305985129fed5d4b1",
"assets/images/TARACO.png": "eec0c4c258bbe15832bfa9d2b6134d0f",
"assets/images/verde.png": "8ce361d95ab48c27df0a375cad0a1cf6",
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
"index.html": "75cea5f4471e8fbd4e9e9f19e718fa3d",
"/": "75cea5f4471e8fbd4e9e9f19e718fa3d",
"main.dart.js": "6a7e7961c1e224442273a681434317f3",
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
