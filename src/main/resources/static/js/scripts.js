// JS FOR THE FACE DETECTION
import * as faceapi from 'https://cdn.jsdelivr.net/npm/face-api.js@0.22.2/+esm';
import axios from 'https://cdn.jsdelivr.net/npm/axios@1.6.8/+esm';

document.addEventListener('DOMContentLoaded', function () {
    const loginButton = document.getElementById('loginButton');
    const closeModalButton = document.getElementById('closeModal');
    const retryButton = document.getElementById('retryButton');

    loginButton.onclick =function (){
        document.getElementById('modal').classList.remove('hidden');
        initializeCamera();
    };

    closeModalButton.onclick = function (){
        document.getElementById('modal').classList.add('hidden');
        stopVideoStream();
    };

    retryButton.onclick = function (){
        captureAndSend();
    };
});

async function initializeCamera() {
    const video = document.getElementById('video');
    if(!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
        document.getElementById('errorMessage').textContent = 'Your browser does not support the camera API';
        return;
    }
    try{
        video.srcObject = await navigator.mediaDevices.getUserMedia({video: true});
        video.onloadedmetadata = async () => {
         await loadFaceDetectionModel();
         captureAndSend();
     }
    } catch (error) {
        document.getElementById('errorMessage').textContent = 'Error accessing the camera';
    }
}

async function loadFaceDetectionModel() {
    try{
     await faceapi.nets.tinyFaceDetector.loadFromUri('/models');
    } catch (error) {
        document.getElementById('errorMessage').textContent = 'Error loading the face detection model';
    }
}

async function captureAndSend() {
    const video = document.getElementById('video');
    const detection = await faceapi.detectSingleFace(video, new faceapi.TinyFaceDetectorOptions());
    if(detection){
        const canvas = faceapi.createCanvasFromMedia(video);
        document.body.append(canvas);
        const base64Image = canvas.toDataURL('image/jpeg');
        await sendImageToBackend(base64Image);
    } else {
        document.getElementById('errorMessage').textContent = 'No face detected';
    }
}

//VER ESTE METODO -> SU FUNCION ES MANDAR LA IMAGEN AL CONTROLLER DE SPRING PARA PROCESSAR EL LOGIN
// -> SI EL LOGIN ES CORRECTO, SE REDIRECCIONA A LA PAGINA PRINCIPAL -> VER COMO ACCEDER AL SESSION DE SPRING
const sendImageToBackend = async (base64Image) => {
    const response = await axios.post('/auth/face-detection', {image: base64Image});
    try{
        if(response.status === 200){
            document.getElementById('modal').classList.add('hidden');
            stopVideoStream();
            document.getElementById('loginForm').submit();
        }
    } catch (error) {
        document.getElementById('errorMessage').textContent = 'Error authenticating';
    }
}

function stopVideoStream() {
    const video = document.getElementById('video');
    if (video.srcObject){
        video.srcObject.getTracks().forEach(track => track.stop());
    }
}